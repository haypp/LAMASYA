package com.lamasya.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.lamasya.R
import com.lamasya.data.remote.contacts.ContactsResponse
import com.lamasya.databinding.ItemCallBinding
import com.lamasya.ui.view.DetailContactActivity

class ContactsAdapter(private val data: ArrayList<ContactsResponse>)
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemCallBinding) : RecyclerView.ViewHolder(binding.root) {
        private var photo = binding.ivContent
        private var call = binding.tvCall
        private var address = binding.tvAddress
        private var name = binding.tvInstansi
        private var jenis = binding.tvTypeRS

        fun bind(contact: ContactsResponse) {
            name.text = contact.nama
            address.text = contact.alamat
            call.text = contact.no_telepon
            jenis.text = contact.jenis
            Glide.with(itemView.context)
                .load(contact.gambar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.placeholderOf(R.drawable.icon_park_loading_one).error(R.drawable.ic_round_broken_image))
                .into(photo)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailContactActivity::class.java)
                intent.putExtra("contact", contact)
                itemView.context.startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                        .toBundle()
                )
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCallBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}