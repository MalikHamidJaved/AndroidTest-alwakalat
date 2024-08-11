package com.skipper.androidtest_alwakalat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.skipper.androidtest_alwakalat.customView.CustomProgressDialogFragment
import com.skipper.androidtest_alwakalat.customView.showCustomSnackbar
import com.skipper.androidtest_alwakalat.data.LoginVM
import com.skipper.androidtest_alwakalat.databinding.LoginLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LoginLayoutBinding
    private lateinit var viewModel: LoginVM
    private var progressDialogFragment: CustomProgressDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this).get(LoginVM::class.java)



        binding.btnSubmit.setOnClickListener {
            showProgressIndicator()
            viewModel.loginUser(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                this@MainActivity
            ) { isSuccess, message ->
                runOnUiThread {
                    hideProgressIndicator()
                    if (!isSuccess) {
                        showCustomSnackbar(binding.root, message)
                    }
                }

            }

        }

    }

    private fun showProgressIndicator() {
        progressDialogFragment = CustomProgressDialogFragment()
        progressDialogFragment?.show(supportFragmentManager, "custom_progress_dialog")
    }

    private fun hideProgressIndicator() {
        if (progressDialogFragment != null)
            progressDialogFragment?.dismiss()
        progressDialogFragment = null
    }

}
