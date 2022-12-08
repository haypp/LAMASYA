package com.lamasya.ui.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lamasya.ui.view.main.MainActivity
import com.lamasya.R
import com.lamasya.data.remote.Register.RegisterRequest
import com.lamasya.data.remote.Register.RegisterResponse
import com.lamasya.databinding.ActivityRegisterBinding
import com.lamasya.ui.auth.UserAuth
import com.lamasya.ui.view.login.LoginActivity
import com.lamasya.ui.viewmodel.RegisterViewModel
import com.lamasya.util.intent
import com.lamasya.util.toast

class RegisterActivity : AppCompatActivity(), UserAuth {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    private val registerVM: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        registerVM.userAuth = this

        binding.btnSignUpEmail.setOnClickListener {
            getValue()
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            intent(LoginActivity::class.java)
            finish()
        }
    }

    override fun onSuccess(registerResponse: LiveData<RegisterResponse>) {
        toast(registerResponse.value!!.message)
        intent(MainActivity::class.java)
        finish()
        LoginActivity().finish()

    }

    override fun onFailure(registerResponse: LiveData<RegisterResponse>) {
        toast(registerResponse.value!!.message)
    }

    private fun registerUser() {
        var validForm = true
        binding.apply {
            if (cbAccept.isChecked) {
                if (FNAME == "") {
                    etFname.error = getString(R.string.please_fill_field)
                    validForm = false
                }
                if (LNAME == "") {
                    etLname.error = getString(R.string.please_fill_field)
                    validForm = false
                }
                if (PHONE == "") {
                    etPhone.error = getString(R.string.please_fill_field)
                    validForm = false
                }
                if (AGE == 0) {
                    etAge.error = getString(R.string.please_fill_field)
                    validForm = false
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                    etEmail.error = getString(R.string.please_fill_field)
                    validForm = false
                }
                if (PASSWORD.length < 6) {
                    etPassword.error = getString(R.string.invalid_password)
                    validForm = false
                }
                createAccount(validForm)
            } else {
                validForm = false
                toast(getString(R.string.please_accept_term))
            }
        }
    }
    private fun createAccount(validForm: Boolean) {
        if(validForm){
            registerVM.register(
                RegisterRequest(
                    FNAME,
                    LNAME,
                    EMAIL,
                    PASSWORD,
                    PHONE,
                    AGE
                )
            )
        }
    }


    private fun getValue() {
        binding.apply {
            FNAME = etFname.text.toString()
            LNAME = etLname.text.toString()
            EMAIL = etEmail.text.toString()
            PASSWORD = etPassword.text.toString()
            PHONE = etPhone.text.toString()

            AGE = if(etAge.text.isNullOrEmpty()){
                0
            } else{
                Integer.valueOf(etAge.text.toString())
            }

        }
    }

    companion object {
        private var FNAME = ""
        private var LNAME = ""
        private var EMAIL = ""
        private var PASSWORD = ""
        private var PHONE = ""
        private var AGE = 0
    }

}