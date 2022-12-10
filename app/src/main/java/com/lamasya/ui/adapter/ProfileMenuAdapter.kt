package com.lamasya.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.lamasya.R
import com.lamasya.data.model.MenuProfileModel
import com.lamasya.ui.view.login.LoginActivity
import com.lamasya.ui.view.profile.ChangePasswordActivity
import com.lamasya.ui.view.profile.DetailProfileActivity
import com.lamasya.util.intent
import com.lamasya.util.toast


class ProfileMenuAdapter(private val itemList: ArrayList<MenuProfileModel>,
        private val loginID :String) :
    RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTittle: TextView = itemView.findViewById(R.id.tv_item_menu_profile)
        var imgTittle: ImageView = itemView.findViewById(R.id.ic_item_menu_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_menu_profile, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            val (image, title) = itemList[position]
            viewHolder.apply {
                Glide.with(itemView.context).load(image)
                    .into(imgTittle)
                tvTittle.text = title
            }
        }

        viewHolder.itemView.setOnClickListener{
            val context = viewHolder.itemView.context
            when (position) {
                0 -> {
                    context.intent(DetailProfileActivity::class.java)
                }
                1 -> {
                    if (loginID == "google.com") {
                        context?.toast("Anda login dengan Google, silahkan login ulang untuk mengganti password")
                    }else {
                        context.intent(ChangePasswordActivity::class.java)
                    }
                }
                2 -> {
                    FirebaseAuth.getInstance().signOut()
                    context.intent(LoginActivity::class.java)
                    (context as Activity).finish()

                }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

}