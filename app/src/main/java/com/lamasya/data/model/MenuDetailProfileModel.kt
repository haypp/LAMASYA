package com.lamasya.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuDetailProfileModel(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("data")
    var data: String? = null
) : Parcelable