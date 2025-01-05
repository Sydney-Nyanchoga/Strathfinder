package com.example.strathfinderapp.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.strathfinderapp.R
import com.example.strathfinderapp.adapters.OnboardingAdapter
import com.example.strathfinderapp.adapters.OnboardingItem
import com.example.strathfinderapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
        setupViewPager()
        setupButtons()
    }

    private fun setupOnboardingItems() {
        val onboardingItems = listOf(
            OnboardingItem(
                title = "Welcome to StrathFinder",
                description = "Your personal campus navigation assistant that helps you find your way around Strathmore University",
                image = R.drawable.welcome_illustration
            ),
            OnboardingItem(
                title = "Easy Navigation",
                description = "Get to anywhere you want, navigation is now made easier within the campus",
                image = R.drawable.navigation_illustration
            ),
            OnboardingItem(
                title = "Indoor & Outdoor Mapping",
                description = "Seamlessly navigate both inside buildings and outdoor spaces with detailed maps",
                image = R.drawable.mapping_illustration
            ),
            OnboardingItem(
                title = "Get Started",
                description = "Join us to experience stress-free navigation around Strathmore University",
                image = R.drawable.getstarted_illustration
            )
        )

        onboardingAdapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = onboardingAdapter
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
            }
            binding.indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer.getChildAt(i) as ImageView
            val drawableId = if (i == position) {
                R.drawable.indicator_active
            } else {
                R.drawable.indicator_inactive
            }
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    drawableId
                )
            )
        }
    }

    private fun setupViewPager() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                binding.buttonSignUp.visibility = if (position == onboardingAdapter.itemCount - 1) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        })
    }

    private fun setupButtons() {
        binding.buttonSignUp.setOnClickListener {
            // Navigate to Register Activity
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        binding.viewPager.unregisterOnPageChangeCallback(
            object : OnPageChangeCallback() {}
        )
        super.onDestroy()
    }
}