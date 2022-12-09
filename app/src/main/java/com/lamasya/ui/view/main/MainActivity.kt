package com.lamasya.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lamasya.R
import com.lamasya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.textView6.text = "${Firebase.auth.currentUser?.email}"
//        binding.button2.setOnClickListener {
//            Firebase.auth.signOut()
//            Toast.makeText(this,"Sign Out",Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, LoginActivity::class.java))
//
//
//
//        }

        replaceFragment(ContactsFragment())
        binding.bottomBar.onTabSelected = {
            when (it.id) {
                R.id.home -> {
                    replaceFragment(StoryFragment())
                }
                R.id.contact -> {
                    replaceFragment(ContactsFragment())
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
        }
    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }

}