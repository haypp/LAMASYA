package com.lamasya.ui.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.lamasya.data.remote.story.Storyresponse
import com.lamasya.databinding.FragmentStoryBinding
import com.lamasya.ui.adapter.StoryAdapter
import com.lamasya.ui.view.create.CreateStoryActivity
import com.lamasya.util.logE
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class StoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentStoryBinding
    private lateinit var firestore: FirebaseFirestore
    private val storyList= ArrayList<Storyresponse>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreate.setOnClickListener {
            val intent = Intent (this@StoryFragment.requireContext(),CreateStoryActivity::class.java)
            startActivity(intent)
        }
        binding.swpRefreshStory.setOnRefreshListener {
            onRefresh()
        }
    }
    override fun onResume() {
        super.onResume()
        storyList.clear()
        addItem()
    }

    fun addItem() {
        firestore = FirebaseFirestore.getInstance()
        storyList.clear()
        firestore.collection("stories").orderBy("created_at", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                context?.logE("itemV 1")
                for (document in it.documents) {
                    storyList.add(
                        Storyresponse(
                            document.data!!["uid"].toString(),
                            document.data!!["pic"].toString(),
                            document.data!!["situation"].toString(),
                            document.data!!["desc"].toString(),
                            document.data!!["nama"].toString(),
                            document.data!!["created_at"].toString(),
                            document.data!!["profil_url"].toString()
                        )
                    )
                context?.logE("itemV 2")
                }
                showRecyclerList()
            }
        }

    private fun showRecyclerList() {
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(binding.root.context)
        val storyAdapter = StoryAdapter(storyList)
        binding.rvStory.adapter = storyAdapter
        storyAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        binding.apply {
            swpRefreshStory.isRefreshing = true
            Timer().schedule(2000) {
                swpRefreshStory.isRefreshing = false
                onResume()
            }
        }
    }

}
