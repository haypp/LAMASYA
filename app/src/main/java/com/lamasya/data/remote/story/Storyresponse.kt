package com.lamasya.data.remote.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storyresponse(
    @SerializedName("profil_url")
    val profil_url: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("situation")
    val situation: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("pic")
    val pic: String?
) : Parcelable
