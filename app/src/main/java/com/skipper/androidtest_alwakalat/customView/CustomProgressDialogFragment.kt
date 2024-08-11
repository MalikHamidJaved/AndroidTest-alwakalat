package com.skipper.androidtest_alwakalat.customView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skipper.androidtest_alwakalat.databinding.LayoutProgressDialogBinding

class CustomProgressDialogFragment : DialogFragment() {

    private lateinit var binding: LayoutProgressDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutProgressDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Customize your dialog here, e.g., set text, start animation, etc.
        binding.textProgressBar.text = "Loading.."
        // Start Lottie animation
        binding.animationView.playAnimation()
    }
}
