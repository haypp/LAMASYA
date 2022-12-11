package com.lamasya.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamasya.data.remote.story.CommentResponse
import com.lamasya.databinding.ItemCommentBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentsAdapter(private val data: ArrayList<CommentResponse>)
    : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        private var photo = binding.ivItemDtProfile
        private var name = binding.tvItemDtUsername
        private var createdAt = binding.tvTime
        private var comment = binding.tvItemDtKomen

        fun bind(commentResponse: CommentResponse) {
            val profileImage = commentResponse.profile_url.toString()
            val sdf = SimpleDateFormat("dd MMM,yyyy HH:mm")
            val commentDate = Date(commentResponse.created_at!!.toLong())

            name.text = commentResponse.name
            createdAt.text = sdf.format(commentDate).toString()
            comment.text = commentResponse.comment
            if(profileImage != "null"){
                Glide.with(itemView.context)
                    .load(profileImage)
                    .circleCrop()
                    .into(photo)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}