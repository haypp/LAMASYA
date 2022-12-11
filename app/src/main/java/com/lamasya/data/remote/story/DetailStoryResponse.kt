package com.lamasya.data.remote.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailStoryResponse(
    @SerializedName("story_id")
    val story_id: String?,
    @SerializedName("author_id")
    val author_id: String?,
    @SerializedName("profile_url")
    val profile_url: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("situation")
    val situation: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("pic")
    val pic: String?,
    @SerializedName("Comment")
    val Comment: ArrayList<CommentResponse>? = null,
) : Parcelable
