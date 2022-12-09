package com.lamasya.ui.viewmodel

import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        firestore.collection("detail_user").whereEqualTo("uid", currentUID)
            .get()
            .addOnSuccessListener {
                mutableProfileResponse.value = ProfileResponse(
                    it.documents[0].data!!["first_name"].toString(),
                    it.documents[0].data!!["last_name"].toString(),
                    it.documents[0].data!!["phone"].toString(),
                    Integer.valueOf(it.documents[0].data!!["age"].toString())
                )
                profileAuth?.onSuccess(liveDataProfileResponse)
            }
    }
}
