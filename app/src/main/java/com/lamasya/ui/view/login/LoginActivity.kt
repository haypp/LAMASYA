package com.lamasya.ui.view.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
    private lateinit var googleSignInClient: GoogleSignInClient

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnsignIngoogle.setOnClickListener {
            signIn()
        }
        binding.btnSignInEmail.setOnClickListener {
            loading.isLoading(true)
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
                etEmail.error = getString(R.string.please_fill_field)
                etPassword.error = getString(R.string.invalid_password)
                etEmail.requestFocus()
            } else {
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
                Log.d("LoginActivity", "cekIsiLiveData: $user")
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }


    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                loginViewModel.firebaseLoginGoogle(account.idToken!!)
                cekIsiLiveData()
            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        cekIsiLiveData()
    }
}