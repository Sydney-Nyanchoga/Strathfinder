package com.example.strathfinderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.strathfinderapp.R
import com.example.strathfinderapp.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    // Initialize view binding and Firebase components
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up view binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Configure Google Sign In with your web client ID
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up all click listeners
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Handle regular email/password registration
        binding.btnSignUp.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validateInput(firstName, lastName, email, password, confirmPassword)) {
                registerUser(firstName, lastName, email, password)
            }
        }

        // Handle Google Sign In
        binding.btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        // Handle navigation back
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        // Navigate to Login Activity when "Log in" is clicked
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        // Validate first name
        if (firstName.isEmpty()) {
            binding.etFirstName.error = "First name is required"
            return false
        }
        // Validate last name
        if (lastName.isEmpty()) {
            binding.etLastName.error = "Last name is required"
            return false
        }
        // Validate email
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Please enter a valid email"
            return false
        }
        // Validate password
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        // Validate password confirmation
        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Please confirm your password"
            return false
        }
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }
        return true
    }

    private fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        // Create user account with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get current user and save additional details to database
                    val user = auth.currentUser
                    val userRef = database.reference.child("users").child(user?.uid ?: "")

                    // Create user data map
                    val userData = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email
                    )

                    // Save user data to database
                    userRef.setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show()
                            // Navigate to main screen
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Show error if registration fails
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Set up activity result launcher for Google Sign In
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Get Google Sign In account
            val account = task.getResult(ApiException::class.java)
            // Authenticate with Firebase using Google credentials
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        // Launch Google Sign In intent
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // Create credentials from Google ID token
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get user and save Google account details
                    val user = auth.currentUser
                    val userRef = database.reference.child("users").child(user?.uid ?: "")

                    // Create user data map from Google account info
                    val userData = hashMapOf(
                        "firstName" to user?.displayName?.split(" ")?.get(0),
                        "lastName" to user?.displayName?.split(" ")?.getOrNull(1),
                        "email" to user?.email
                    )

                    // Save Google user data to database
                    userRef.setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successfully signed in with Google!",
                                Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                } else {
                    Toast.makeText(this, "Google sign in failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}