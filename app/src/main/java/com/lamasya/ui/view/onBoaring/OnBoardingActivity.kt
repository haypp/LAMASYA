package com.lamasya.ui.view.onBoaring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lamasya.R
import com.lamasya.databinding.ActivityOnBoardingBinding
import com.lamasya.ui.view.login.LoginActivity
import com.lamasya.util.intent

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNext.setOnClickListener {
            intent(LoginActivity::class.java)
            finish()
        }
    }
}