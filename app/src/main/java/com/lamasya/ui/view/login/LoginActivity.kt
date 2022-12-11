package com.lamasya.ui.view.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lamasya.R
import com.lamasya.databinding.ActivityLoginBinding
import com.lamasya.ui.view.main.MainActivity
import com.lamasya.ui.view.register.RegisterActivity
import com.lamasya.ui.viewmodel.LoginViewModel
import com.lamasya.util.LoadingDialog
import com.lamasya.util.intent
import com.lamasya.util.toast


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignInEmail.setOnClickListener {
            getEmailPw()
        }
        binding.btnRegister.setOnClickListener {
            intent(RegisterActivity::class.java)
        }
        binding.btnForgotPassword.setOnClickListener {
            intent(ResetPasswordActivity::class.java)
        }
    }

    private fun getEmailPw() {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty() || password.length < 6) {
                if(email.isEmpty() ){
                    etEmail.error =getString(R.string.please_fill_field)
                }
                if( password.isEmpty() || password.length < 6) {
                    etPassword.error = getString(R.string.invalid_password)
                }
            } else {
                loading.isLoading(true)
                signInEmail(email, password)
            }
        }
    }

    private fun signInEmail(email: String, password: String) {
        loginViewModel.loginEmail(email, password)
        cekIsiLiveData()
    }

    private fun cekIsiLiveData() {
        val auth = Firebase.auth
        if (auth.currentUser != null) {
            intent(MainActivity::class.java)
            finish()
        } else {
            loginViewModel.loginfirebase.observe(this) { user ->
                if (user != null) {
                    loading.isLoading(false)
                    intent(MainActivity::class.java)
                    finish()
                } else {
                    loading.isLoading(false)
                    toast(getString(R.string.invalid_email_or_password))
                }
            }
        }

    }
    override fun onStart() {
        super.onStart()
        cekIsiLiveData()
    }
}