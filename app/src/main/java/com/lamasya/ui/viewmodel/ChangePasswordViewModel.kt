package com.lamasya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lamasya.ui.auth.PasswordAuth
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {
    var passwordAuth: PasswordAuth? = null

    fun setPassword(password: String, newPassword: String) = viewModelScope.launch {
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)?.addOnCompleteListener {
                        if(it.isSuccessful){
                            passwordAuth?.onSuccess("Password Telah di Update")
                        }
                    }
                } else {
                    passwordAuth?.onFailed("Password Salah")
                }
            }

    }

}