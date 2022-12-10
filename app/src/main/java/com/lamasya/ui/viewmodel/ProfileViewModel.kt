package com.lamasya.ui.viewmodel

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lamasya.data.remote.profile.ProfileRequest
import com.lamasya.data.remote.profile.ProfileResponse
import com.lamasya.ui.auth.ProfileAuth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val mutableProfileResponse = MutableLiveData<ProfileResponse>()
    private val liveDataProfileResponse: LiveData<ProfileResponse> =
        mutableProfileResponse
    var profileAuth: ProfileAuth? = null

    fun getProfile(currentUID: String) = viewModelScope.launch {
        firestore.collection("detail_user").document(currentUID)
            .get()
            .addOnSuccessListener {
                mutableProfileResponse.value = ProfileResponse(
                    it.data!!["first_name"].toString(),
                    it.data!!["last_name"].toString(),
                    FirebaseAuth.getInstance().currentUser?.email.toString(),
                    it.data!!["phone"].toString(),
                    it.data!!["gender"].toString(),
                    Integer.valueOf(it.data!!["age"].toString())
                )
                profileAuth?.onSuccess(liveDataProfileResponse)
            }

    }

    fun updateProfile(profileRequest: ProfileRequest) = viewModelScope.launch {
        firestore.collection("detail_user").document(profileRequest.user_Id).update(
            "first_name", profileRequest.first_name,
            "last_name", profileRequest.last_name,
            "phone", profileRequest.phone,
            "age", profileRequest.age,
            "gender", profileRequest.gender
        )
    }
}
