package com.lamasya.ui.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lamasya.data.remote.story.Storyresponse
import com.lamasya.databinding.FragmentStoryBinding
import com.lamasya.ui.adapter.StoryAdapter
import com.lamasya.ui.view.create.CreateStoryActivity
import com.lamasya.util.logE


class StoryFragment : Fragment() {
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
        initVars()
        addItem()
        binding.buttonCreate.setOnClickListener {
            val intent = Intent (this@StoryFragment.requireContext(),CreateStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initVars() {
        binding.rvStory.setHasFixedSize(true)

    }

    private fun addItem() {
        firestore = FirebaseFirestore.getInstance()
        storyList.clear()
        firestore.collection("stories").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val uid = document.getString("uid")
                    val pic = document.getString("pic")
                    val situation = document.getString("situation")
                    val desc = document.getString("desc")
                    val nama = document.getString("nama")
                    val story = Storyresponse(uid, pic, situation, desc, nama)
                    storyList.add(story)
                }
                showRecyclerList()
            }
        }

    private fun showRecyclerList() {
        binding.rvStory.layoutManager = LinearLayoutManager(binding.root.context)
        val storyAdapter = StoryAdapter(storyList)
        binding.rvStory.adapter = storyAdapter
    }

}
