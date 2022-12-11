package com.lamasya.ui.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.lamasya.R
import com.lamasya.data.remote.story.CommentResponse
import com.lamasya.data.remote.story.DetailStoryResponse
import com.lamasya.databinding.ActivityDetailStoryBinding
import com.lamasya.ui.adapter.CommentsAdapter
import com.lamasya.ui.auth.DetailStoryAuth
import com.lamasya.ui.view.main.MainActivity
import com.lamasya.ui.viewmodel.DetailStoryViewModel
import com.lamasya.util.LoadingDialog
import com.lamasya.util.logE
import java.text.SimpleDateFormat
import com.lamasya.util.toast
import java.util.*

class DetailStoryActivity : AppCompatActivity(), DetailStoryAuth {
    private lateinit var binding: ActivityDetailStoryBinding
    private val detailStoryViewModel: DetailStoryViewModel by viewModels()
    private val loading = LoadingDialog(this)
    private val itemListComment = ArrayList<CommentResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading.isLoading(true)
        getCommentList()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.tittle_comment)

        detailStoryViewModel.detailStoryAuth = this

        val storyID = intent.getStringExtra(EXTRA_STORY_ID).toString()
        val authorID = intent.getStringExtra(EXTRA_AUTHOR_ID).toString()
        logE("get Story ara $storyID")
        detailStoryViewModel.getDetailStory(storyID, authorID)

        binding.ivSendComment.setOnClickListener{
            sendComment()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) this.finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(storyResponse: LiveData<DetailStoryResponse>) {
        loading.isLoading(false)
        val value = storyResponse.value
        val sdf = SimpleDateFormat("dd MMM,yyyy HH:mm")
        val postDate = Date(value?.created_at!!.toLong())


        logE("get Story detail $value")
        binding.apply {
            setSituation(value.situation.toString())
            if (value.profile_url != "null") {
                Glide.with(this@DetailStoryActivity)
                    .load(value.profile_url)
                    .circleCrop()
                    .into(ivDsProfile)
            }
            Glide.with(this@DetailStoryActivity)
                .load(value.pic)
                .into(ivDsContent)

            tvDsUsername.text = value.name
            tvDsSituation.text = value.situation
            tvDsTime.text = sdf.format(postDate).toString()
            tvDsDesc.text = value.desc
        }
    }

    private fun sendComment() {
        COMMENT = binding.etComment.text.toString()
        if(COMMENT == ""){
            binding.etComment.error = getString(R.string.please_fill_field)
        }
        else{
            loading.isLoading(true)
            val currentUID = MainActivity.CURRENT_UID
            val map = hashMapOf(
                "user_id" to currentUID,
                "created_at" to System.currentTimeMillis(),
                "comment" to COMMENT,
            )

            FirebaseFirestore.getInstance().collection("stories").document(intent.getStringExtra(EXTRA_STORY_ID).toString())
                .collection("comments").add(map).addOnCompleteListener {
                    loading.isLoading(false)
                    toast(getString(R.string.comment_success))
                    binding.etComment.text = Editable.Factory.getInstance().newEditable("")
                    getCommentList()
                }
        }
    }
    private fun setSituation(situation: String) {
        when (situation) {
            "lalu lintas" -> {
                binding.ivDsTypeSituation.setBackgroundResource(R.drawable.lalu_lintas)
            }
            "bencana alam" -> {
                binding.ivDsTypeSituation.setBackgroundResource(R.drawable.bencana_alam)
            }
            "kesehatan" -> {
                binding.ivDsTypeSituation.setBackgroundResource(R.drawable.kesehatan)
            }
        }
    }

    private fun getCommentList() {
        itemListComment.clear()
        val storyId = intent.getStringExtra(EXTRA_STORY_ID).toString()

        FirebaseFirestore.getInstance().collection("stories").document(storyId)
            .collection("comments").get().addOnSuccessListener {
                for (document in it.documents) {
                    val commentUID = document.data!!["user_id"].toString()
                    FirebaseFirestore.getInstance().collection("detail_user")
                        .document(commentUID)
                        .get()
                        .addOnSuccessListener { it ->
                            val fName = it.data!!["first_name"].toString()
                            val lName = it.data!!["last_name"].toString()
                            val name = "$fName $lName"
                            itemListComment.add(
                                CommentResponse(
                                    it.data!!["profile_pict"].toString(),
                                    name,
                                    document.data!!["created_at"].toString(),
                                    document.data!!["comment"].toString()
                                )
                            )
                            showRecyclerList()
                        }
                }
            }
    }

    private fun showRecyclerList() {
        binding.apply {
            rvListComment.layoutManager = LinearLayoutManager(binding.root.context)
            val listMenuAdapter = CommentsAdapter(itemListComment)
            rvListComment.adapter = listMenuAdapter
            rvListComment.setHasFixedSize(true)
        }
    }

    companion object {
        private var COMMENT = ""
        const val EXTRA_STORY_ID = "EXTRA_STORY_ID"
        const val EXTRA_AUTHOR_ID = "EXTRA_AUTHOR_ID"
    }
}