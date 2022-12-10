package com.lamasya.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lamasya.R
import com.lamasya.databinding.ActivityResetPasswordBinding
import com.lamasya.ui.viewmodel.ResetPwViewModel
import com.lamasya.util.LoadingDialog
import com.lamasya.util.toast

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val resetPwViewModel: ResetPwViewModel by viewModels()
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = binding.etEmailReset.text.toString()
        if (email.isNotEmpty()) {
            loading.isLoading(true)
            resetPwViewModel.resetPassword(email)
            resetPwViewModel.resetpw.observe(this) {
                if (it) {
                    loading.isLoading(false)
                    toast(getString(R.string.password_request_has_been_sent))
                    finish()
                } else {
                    loading.isLoading(false)
                    toast(getString(R.string.email_not_found))
                }
            }
        } else {
            toast(getString(R.string.email_cant_null))
        }
    }
}
