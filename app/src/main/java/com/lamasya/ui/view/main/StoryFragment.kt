package com.lamasya.ui.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lamasya.databinding.FragmentStoryBinding
import com.lamasya.ui.view.create.CreateStoryActivity


class StoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind = FragmentStoryBinding.inflate(layoutInflater)

        bind.buttonCreate.setOnClickListener {
            val intent = Intent (this@StoryFragment.requireContext(),CreateStoryActivity::class.java)
            startActivity(intent)
        }

        return bind.root

    }

}
