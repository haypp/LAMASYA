package com.lamasya.data.remote.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterRequest(
    @SerializedName("first_name")
    var first_name: String,

    @SerializedName("last_name")
    var last_name: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("age")
    var age: Int,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("gender")
    var gender: String,

) : Parcelable