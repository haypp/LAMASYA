package com.lamasya.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginViewModel : ViewModel() {
    private val firestore = Firebase.firestore
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
    fun firebaseLoginGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    _loginfirebase.value = user
                    isRegistered()
                } else {
                    _loginfirebase.value = null
                }
            }
    }
    private fun isRegistered() {
        val user = Firebase.auth.currentUser!!.uid
        Log.d("user", user)
        firestore.collection("detail_user").document(user).get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d("TAG", "DocumentSnapshot data: User Registered")
                    Log.d("TAG", document.data.toString())
                } else {
                    saveDataFireStore()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    private fun saveDataFireStore() {
        val data = hashMapOf(
            "first_name" to "default",
            "last_name" to "default",
            "phone" to "default",
            "age" to 0,
            "gender" to "Laki - Laki"
        )

        firestore.collection("detail_user")
            .document(Firebase.auth.currentUser!!.uid).set(data)
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error writing document", e)
            }
    }
}