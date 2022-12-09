package com.lamasya.data.remote.contacts

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactsResponse(
    @SerializedName("nama")
    var nama: String,
    @SerializedName("alamat")
    var alamat: String,
    @SerializedName("no_telepon")
    var no_telepon: String? = null,
    @SerializedName("gambar")
    var gambar: String? = null,
    @SerializedName("link_maps")
    var link_maps: String,
    @SerializedName("jenis")
    var jenis: String? = null,
) : Parcelable
