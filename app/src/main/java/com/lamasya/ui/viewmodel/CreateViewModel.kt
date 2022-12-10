package com.lamasya.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreateViewModel : ViewModel() {
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var _uploadStory = MutableLiveData<Boolean>()
    val uploadStoryData: LiveData<Boolean> = _uploadStory

    fun uploadStory(
        uid: String?,
        desc: String,
        situation: String,
        name: String,
        imageUri: Uri?,
        pict: String
    ) {
        storageRef = FirebaseStorage.getInstance().reference.child("Images/stories")
        firebaseFirestore = FirebaseFirestore.getInstance()
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = hashMapOf(
                            "uid" to uid,
                            "pic" to uri.toString(),
                            "desc" to desc,
                            "situation" to situation,
                            "nama" to name,
                            "created_at" to System.currentTimeMillis(),
                            "profil_url" to pict
                        )
                        firebaseFirestore.collection("stories")
                            .add(map).addOnCompleteListener { firestoreTask ->
                                _uploadStory.value = firestoreTask.isSuccessful
                            }
                    }
                } else {
                    _uploadStory.value = false
                }
            }
        }
    }
}