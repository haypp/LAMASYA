package com.lamasya.ui.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lamasya.databinding.ActivityChangeDetailProfileBinding

class ChangeDetailProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeDetailProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChangeDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)

    }
}