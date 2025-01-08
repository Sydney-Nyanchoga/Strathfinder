package com.example.strathfinderapp.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.strathfinderapp.R
import com.example.strathfinderapp.adapters.MenuAdapter
import com.example.strathfinderapp.databinding.ActivityMainBinding
import com.example.strathfinderapp.models.MenuItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupMenuGrid()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)  // Hide default title
        }
    }

    private fun setupMenuGrid() {
        val menuItems = listOf(
            MenuItem(
                id = 1,
                title = "Campus Map",
                description = "Interactive campus navigation",
                icon = R.drawable.ic_map,
                activityClass = MapActivity::class.java
            ),
            MenuItem(
                id = 2,
                title = "Buildings",
                description = "Find lecture halls and offices",
                icon = R.drawable.ic_building,
                activityClass = BuildingsActivity::class.java
            ),
            MenuItem(
                id = 3,
                title = "Health Services",
                description = "Medical center and pharmacy",
                icon = R.drawable.ic_health,
                activityClass = ServicesActivity::class.java
            ),
            MenuItem(
                id = 4,
                title = "Sports",
                description = "Sports facilities and grounds",
                icon = R.drawable.ic_sports,
                activityClass = SportsActivity::class.java
            ),
            MenuItem(
                id = 5,
                title = "Cafeteria",
                description = "Food courts and restaurants",
                icon = R.drawable.ic_cafeteria,
                activityClass = CafeteriaActivity::class.java
            ),
            MenuItem(
                id = 6,
                title = "Offices",
                description = "Administrative offices",
                icon = R.drawable.ic_offices,
                activityClass = OfficesActivity::class.java
            ),
            MenuItem(
                id = 7,
                title = "Gates",
                description = "Campus entry and exit points",
                icon = R.drawable.ic_gates,
                activityClass = EntranceActivity::class.java
            ),
            MenuItem(
                id = 8,
                title = "About",
                description = "App information and help",
                icon = R.drawable.ic_about,
                activityClass = AboutActivity::class.java
            )
        )

        val adapter = MenuAdapter(menuItems) { menuItem ->
            try {
                startActivity(Intent(this, menuItem.activityClass))
            } catch (e: Exception) {
                // Handle any navigation errors
                showErrorDialog(
                    "This feature is coming soon!",
                    "We're working hard to bring you this feature. Please check back later."
                )
            }
        }

        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2) // 2 columns
            this.adapter = adapter
        }
    }

    private fun showErrorDialog(title: String, message: String) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onBackPressed() {
        // Show exit confirmation dialog
        MaterialAlertDialogBuilder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("No", null)
            .show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}