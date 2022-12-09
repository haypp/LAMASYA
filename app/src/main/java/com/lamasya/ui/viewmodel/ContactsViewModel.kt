package com.lamasya.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lamasya.data.remote.contacts.ContactsResponse
import com.lamasya.ui.auth.ProfileAuth

class ContactsViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val _dataRS = MutableLiveData<ContactsResponse?>()
    val dataRS: LiveData<ContactsResponse?> = _dataRS

    fun getRS(paramC : String) {
        _dataRS.value=null
        firestore.collection(paramC).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    _dataRS.value = ContactsResponse(
                        document.data["nama"].toString(),
                        document.data["alamat"].toString(),
                        document.data["no_telepon"].toString(),
                        document.data["gambar"].toString(),
                        document.data["link_maps"].toString(),
                        document.data["jenis"].toString()
                    )
                }
            }
            .addOnFailureListener {
                _dataRS.value = ContactsResponse("Error", "Error", "Error","Error", "Error", "Error")
            }
    }
}