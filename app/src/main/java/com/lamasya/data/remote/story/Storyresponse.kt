package com.lamasya.data.remote.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storyresponse(
    @SerializedName("uid")
    val id: String?,
    @SerializedName("pic")
    val pic: String?,
    @SerializedName("situation")
    val situation: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("nama")
    val nama: String?,
) : Parcelable
