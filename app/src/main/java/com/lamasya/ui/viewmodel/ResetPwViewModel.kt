package com.lamasya.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPwViewModel : ViewModel() {
    private var _resetpw = MutableLiveData<Boolean>()
    val resetpw: LiveData<Boolean> = _resetpw

    fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _resetpw.value = task.isSuccessful
            }
    }
}