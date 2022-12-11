package com.lamasya.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lamasya.R
import com.lamasya.databinding.ActivityMainBinding
import com.lamasya.util.logE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentUid()

        replaceFragment(ContactsFragment())
        binding.bottomBar.onTabSelected = {
            when (it.id) {
                R.id.home -> replaceFragment(StoryFragment())
                R.id.contact -> replaceFragment(ContactsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
        }
    }

    private fun getCurrentUid() {
        firebaseauth = Firebase.auth
        CURRENT_UID = firebaseauth.currentUser!!.uid
        logE("ara auth $CURRENT_UID")

    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }

    companion object{
        var CURRENT_UID = ""
    }

}