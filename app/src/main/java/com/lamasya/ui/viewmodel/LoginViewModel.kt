package com.lamasya.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private var _loginfirebase = MutableLiveData<FirebaseUser?>()
    val loginfirebase: LiveData<FirebaseUser?> = _loginfirebase

    fun loginEmail(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    _loginfirebase.value = user
                } else {
                    _loginfirebase.value = null
                }
            }
    }
}