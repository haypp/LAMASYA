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
import com.lamasya.data.remote.story.Storyresponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StoryAdapter(private val data: ArrayList<Storyresponse>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (pp, name, situation, created_at, desc, pict) = data[position]
        Log.e(this@StoryAdapter.toString(), "onBindViewHolder: darta $data" )
        holder.apply {

            tvName.text = name
            tvSituation.text = situation
            tvDesc.text = desc
            when (situation) {
                "lalu lintas" -> {
                    ivSituation.setBackgroundResource(R.drawable.lalu_lintas)
                }
                "bencana alam" -> {
                    ivSituation.setBackgroundResource(R.drawable.bencana_alam)
                }
                "kesehatan" -> {
                    ivSituation.setBackgroundResource(R.drawable.kesehatan)
                }
            }
            val sdf = SimpleDateFormat("dd MMM,yyyy HH:mm")
            val resultdate = Date(created_at!!.toLong())
            tvTime.text = sdf.format(resultdate).toString()
            if (pp != "null") {
                Glide.with(itemView.context)
                    .load(pp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivProfile)
            }

            Glide.with(itemView.context)
                .load(pict)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivContent)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_username)
        var tvSituation: TextView = itemView.findViewById(R.id.type_situation)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        var ivContent: ImageView = itemView.findViewById(R.id.iv_content)
        var ivSituation: ImageView = itemView.findViewById(R.id.iv_type_situation)
        var tvTime : TextView = itemView.findViewById(R.id.tv_time)
        var ivProfile : ImageView = itemView.findViewById(R.id.iv_profile)
    }
}