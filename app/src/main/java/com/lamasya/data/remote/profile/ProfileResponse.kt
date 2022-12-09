package com.lamasya.data.remote.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProfileResponse(
    @SerializedName("first_name")
    var first_name: String,

    @SerializedName("last_name")
    var last_name: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("age")
    var age: Int,
) : Parcelable