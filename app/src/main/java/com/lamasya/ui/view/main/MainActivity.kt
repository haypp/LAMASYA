package com.lamasya.ui.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lamasya.R
import com.lamasya.databinding.ActivityMainBinding
import com.lamasya.ui.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.textView6.text = "${Firebase.auth.currentUser?.email}"
//        binding.button2.setOnClickListener {
//            Firebase.auth.signOut()
//            Toast.makeText(this,"Sign Out",Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, LoginActivity::class.java))
//
//
//
//        }
    }

}