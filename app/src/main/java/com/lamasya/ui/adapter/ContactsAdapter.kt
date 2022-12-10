package com.lamasya.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lamasya.R
import com.lamasya.data.remote.contacts.ContactsResponse

class ContactsAdapter(private val data: ArrayList<ContactsResponse>)
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (nama, alamat, no_telepon, gambar, link_maps) = data[position]
        holder.apply {
            tvName.text = nama
            tvAddress.text = alamat
            tvCall.text = no_telepon
            Glide.with(itemView.context)
                .load(gambar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivContent)
            Log.e("test", "onBindViewHolder: $gambar | $nama")
        }
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_instansi)
        var tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        var tvCall: TextView = itemView.findViewById(R.id.tv_call)
        var tvTypeRS: TextView = itemView.findViewById(R.id.tv_typeRS)
        var ivContent: ImageView = itemView.findViewById(R.id.iv_content)
        var typeRS: ImageView = itemView.findViewById(R.id.iv_icon_rs)
    }
}