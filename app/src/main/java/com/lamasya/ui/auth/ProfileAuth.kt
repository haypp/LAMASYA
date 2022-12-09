package com.lamasya.ui.auth

import androidx.lifecycle.LiveData
import com.lamasya.data.remote.profile.ProfileResponse

interface ProfileAuth {
    fun onSuccess(profileResponse: LiveData<ProfileResponse>)
}