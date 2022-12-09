package com.lamasya.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lamasya.R
import com.lamasya.data.model.MenuDetailProfileModel
import com.lamasya.ui.view.profile.ChangeDetailProfileActivity

class DetailProfileMenuAdapter(private val itemList: ArrayList<MenuDetailProfileModel>) :
    RecyclerView.Adapter<DetailProfileMenuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTittle: TextView = itemView.findViewById(R.id.tv_item_info_title)
        var tvData: TextView = itemView.findViewById(R.id.tv_item_info_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_info_detail_profile, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            val (title, data) = itemList[position]
            viewHolder.apply {
                tvTittle.text = title
                tvData.text = data
            }
        }

        viewHolder.itemView.setOnClickListener{
            val context = viewHolder.itemView.context
            val move = Intent(context, ChangeDetailProfileActivity::class.java)
            move.putExtra(ChangeDetailProfileActivity.EXTRA_ITEM, itemList[position].title)

            context.startActivity(move)
        }
    }

    override fun getItemCount(): Int = itemList.size

}