package com.lamasya.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lamasya.R
import com.lamasya.data.remote.Register.RegisterRequest
import com.lamasya.data.remote.Register.RegisterResponse
import com.lamasya.ui.auth.UserAuth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private lateinit var firebaseauth: FirebaseAuth
    private val firestore = Firebase.firestore
    private val mutableRegisterResponse = MutableLiveData<RegisterResponse>()
    private val liveDataRegisterResponse: LiveData<RegisterResponse> =
        mutableRegisterResponse

    var userAuth: UserAuth? = null

    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        firebaseauth = Firebase.auth

        firebaseauth.createUserWithEmailAndPassword(registerRequest.email, registerRequest.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveDataFireStore(registerRequest)
                } else {
                    mutableRegisterResponse.value = RegisterResponse("Email Already Registered")
                    Log.e(this@RegisterViewModel.toString(), "ara vm dupe error ${liveDataRegisterResponse.value.toString()}")
                    userAuth?.onFailure(liveDataRegisterResponse)
                }
            }
    }

    private fun saveDataFireStore(registerRequest: RegisterRequest) {
        val data = hashMapOf(
            "uid" to firebaseauth.currentUser!!.uid,
            "first_name" to registerRequest.first_name,
            "last_name" to registerRequest.last_name,
            "phone" to registerRequest.phone,
            "age" to registerRequest.age
        )

        firestore.collection("detail_user")
            .add(data)
            .addOnSuccessListener {
                mutableRegisterResponse.value = RegisterResponse("Register Successful")
                Log.e(this@RegisterViewModel.toString(), "ara vm succes ${liveDataRegisterResponse.value.toString()}")
                userAuth?.onFailure(liveDataRegisterResponse)
            }
            .addOnFailureListener { e ->
                mutableRegisterResponse.value = RegisterResponse(StringBuilder(e.message.toString()).toString())
                Log.e(this@RegisterViewModel.toString(), "ara vm error ${liveDataRegisterResponse.value.toString()}")
                userAuth?.onFailure(liveDataRegisterResponse)
            }
    }
}
