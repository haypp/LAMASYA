package com.lamasya.ui.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lamasya.data.remote.contacts.ContactsResponse
import com.lamasya.databinding.ActivityDetailContactBinding

@Suppress("DEPRECATION")
class DetailContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailContactBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemView = intent.getParcelableExtra<ContactsResponse>("contact") as ContactsResponse
        Glide.with(applicationContext)
            .load(itemView.gambar)
            .into(binding.imageView3)
        binding.tvInstansi.text = itemView.nama
        binding.tvAddress.text = itemView.alamat
        binding.tvTypeRS.text = itemView.jenis


        binding.btnCall.setOnClickListener{
            val binding = itemView.no_telepon
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$binding"))
            startActivity(dialPhoneIntent)
        }

        binding.btnMaps.setOnClickListener{
            val binding = itemView.link_maps
            val dialPhoneIntent = Intent(Intent.ACTION_VIEW, Uri.parse(binding))
            intent.setPackage("com.google.android.apps.maps");
            startActivity(dialPhoneIntent)
        }


    }

}