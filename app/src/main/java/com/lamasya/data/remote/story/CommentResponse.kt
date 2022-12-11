package com.lamasya.data.remote.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CommentResponse(
    @SerializedName("profile_url")
    val profile_url: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("comment")
    val comment: String?
) : Parcelable