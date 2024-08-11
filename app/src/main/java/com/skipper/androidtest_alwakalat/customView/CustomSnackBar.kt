package com.skipper.androidtest_alwakalat.customView


import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.skipper.androidtest_alwakalat.R


fun showCustomSnackbar(view: View, message: String, slideUpDuration: Long = 2000L) {
    // Inflate custom Snackbar layout
    val customView = LayoutInflater.from(view.context).inflate(R.layout.custom_error, null)

    // Set the custom message
    val textView: TextView = customView.findViewById(R.id.error_text)
    textView.text = message
    customView.layoutParams = ViewGroup.LayoutParams(view.width,
        ViewGroup.LayoutParams.WRAP_CONTENT)


    // Create a layout to hold the custom Snackbar
    val snackbarContainer = CoordinatorLayout(view.context)
    snackbarContainer.layoutParams = ViewGroup.LayoutParams(
        view.width,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    // Add the custom Snackbar view to the container
    snackbarContainer.addView(customView)

    // Add the container to the root view
    val rootView = (view.rootView as ViewGroup)
    rootView.addView(snackbarContainer)

    // Set the Snackbar to appear at the top
    val layoutParams = snackbarContainer.layoutParams as FrameLayout.LayoutParams
    layoutParams.gravity = Gravity.TOP
    snackbarContainer.layoutParams = layoutParams


    // Ensure the view is measured and laid out before starting animations
    snackbarContainer.post {
        // Initialize the slide-down animation
        val slideDown = TranslateAnimation(
            0f, 0f, -customView.height.toFloat(), 0f
        ).apply {
            duration = 500
            fillAfter = true
        }

        // Initialize the slide-up animation
        val slideUp = TranslateAnimation(
            0f, 0f, 0f, -customView.height.toFloat()
        ).apply {
            duration = 500
            fillAfter = true
        }

        // Start slide-down animation
        snackbarContainer.startAnimation(slideDown)

        // Schedule slide-up animation and removal
        Handler(Looper.getMainLooper()).postDelayed({
            snackbarContainer.startAnimation(slideUp)
            // Remove the Snackbar after the slide-up animation ends
            slideUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    rootView.removeView(snackbarContainer)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }, slideUpDuration)
    }
}