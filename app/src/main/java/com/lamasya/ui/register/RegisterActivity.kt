package com.lamasya.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lamasya.ui.main.MainActivity
import com.lamasya.R
import com.lamasya.databinding.ActivityRegisterBinding
import com.lamasya.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnSignUpEmail.setOnClickListener {
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser() {
        if (binding.cbAccept.isChecked){
            binding.apply {
                val fname = etFname.text.toString()
                val lname = etLname.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val phone = etPhone.text.toString()
                val age = etAge.text.toString()
                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || age.isEmpty()) {
                    etFname.error = "First Name tidak boleh kosong"
                    etLname.error = "Last Name tidak boleh kosong"
                    etPhone.error = "Phone tidak boleh kosong"
                    etAge.error = "Umur tidak boleh kosong"
                    etEmail.error = "Email tidak boleh kosong"
                    etPassword.error = "Password tidak boleh kosong"
                    etEmail.requestFocus()
                    return
                } else {
                    createAccount(email, password)
                }
            }
        } else {
            Toast.makeText(this, "Silahkan centang persetujuan", Toast.LENGTH_SHORT).show()
        }
    }
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            LoginActivity().finish()
        }
    }
}