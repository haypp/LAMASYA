package com.lamasya.data.remote.Register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterResponse(
    @SerializedName("message")
    var message: String,
) : Parcelable