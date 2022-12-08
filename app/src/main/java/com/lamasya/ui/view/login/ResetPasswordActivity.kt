package com.lamasya.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lamasya.R
import com.lamasya.databinding.ActivityResetPasswordBinding
import com.lamasya.ui.viewmodel.ResetPwViewModel
import com.lamasya.util.toast

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val resetPwViewModel: ResetPwViewModel by viewModels()

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
            resetPwViewModel.resetPassword(email)
            resetPwViewModel.resetpw.observe(this) {
                if (it) {
                    toast("Email reset password telah dikirim")
                    finish()
                } else {
                    toast("Email tidak terdaftar")
                }
            }
        } else {
            toast("Email tidak boleh kosong")
        }
    }
}
