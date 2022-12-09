package com.lamasya.data.remote.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProfileRequest(
    @SerializedName("user_Id")
    var user_Id: String,

    @SerializedName("first_name")
    var first_name: String,

    @SerializedName("last_name")
    var last_name: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("gender")
    var gender: String,

    @SerializedName("age")
    var age: Int,
) : Parcelable