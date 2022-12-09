package com.lamasya.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuProfileModel(
    @SerializedName("image")
    var image: Int,

    @SerializedName("title")
    var title: String? = null
) : Parcelable