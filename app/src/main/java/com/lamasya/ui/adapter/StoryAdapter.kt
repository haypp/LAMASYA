package com.lamasya.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.lamasya.R
import com.lamasya.data.remote.story.StoryResponse
import com.lamasya.ui.view.story.DetailStoryActivity
import java.text.SimpleDateFormat
import java.util.*

class StoryAdapter(private val data: ArrayList<StoryResponse>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (story_id, author_id, pp, name, situation, created_at, desc, pict) = data[position]
        Log.e(this@StoryAdapter.toString(), "get Story: darta $story_id $name")
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
            val sdf = SimpleDateFormat("EEE dd MMM yyyy HH:mm")
            val resultdate = Date(created_at!!.toLong())
            tvTime.text = sdf.format(resultdate).toString()
            if (pp != "null") {
                Glide.with(itemView.context)
                    .load(pp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.placeholderOf(R.drawable.icon_park_loading_one).error(R.drawable.ic_round_broken_image))
                    .into(ivProfile)
            }

            Glide.with(itemView.context)
                .load(pict)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivContent)

            holder.itemView.setOnClickListener {
                Log.e(this@StoryAdapter.toString(), "getStory: darta aa $story_id")
                Log.e(this@StoryAdapter.toString(), "getStory: profile aa $author_id")
                val mContext = holder.itemView.context
                val move = Intent(mContext, DetailStoryActivity::class.java)
                move.putExtra(DetailStoryActivity.EXTRA_STORY_ID, story_id.toString())
                move.putExtra(DetailStoryActivity.EXTRA_AUTHOR_ID, author_id.toString())
                mContext.startActivity(move)
            }
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