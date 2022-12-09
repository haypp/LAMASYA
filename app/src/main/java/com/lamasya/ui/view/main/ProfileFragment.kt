package com.lamasya.ui.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamasya.R
import com.lamasya.data.model.MenuProfileModel
import com.lamasya.data.remote.profile.ProfileResponse
import com.lamasya.databinding.FragmentProfileBinding
import com.lamasya.ui.adapter.ProfileMenuAdapter
import com.lamasya.ui.auth.ProfileAuth
import com.lamasya.ui.viewmodel.ProfileViewModel
import com.lamasya.util.logE

class ProfileFragment : Fragment(), ProfileAuth {
    private lateinit var binding: FragmentProfileBinding
    private val itemList = ArrayList<MenuProfileModel>()

    private val profileVM: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileVM.profileAuth = this
        addItem()
        getProfileData()
        binding.rvListMenuProfile.setHasFixedSize(true)
    }

    private fun getProfileData() {
        val currentUID = MainActivity.CURRENT_UID
        context?.logE("ara ID profile fragment $currentUID")
        profileVM.getProfile(currentUID)
    }

    private fun addItem() {
        itemList.clear()
        val listSize = imageListItem.size - 1
        for (i in 0..listSize) {
            itemList.add(
                MenuProfileModel(
                    imageListItem[i],
                    titleListItem[i]
                )
            )
            showRecyclerList()
        }

    }

    private fun showRecyclerList() {
        binding.apply {
            rvListMenuProfile.layoutManager = LinearLayoutManager(root.context)
            val mListProfileAdapter = ProfileMenuAdapter(itemList)
            rvListMenuProfile.adapter = mListProfileAdapter
        }
    }

    companion object {
        private val imageListItem = arrayOf(
            R.drawable.ic_baseline_person_outline_24,
            R.drawable.ic_baseline_vpn_key_24,
            R.drawable.ic_baseline_logout_24
        )
        private val titleListItem = arrayOf("Detail Profile", "Ubah Password", "Keluar Akun")
    }

    override fun onSuccess(profileResponse: LiveData<ProfileResponse>) {
        binding.tvDetailName.text =
            StringBuilder(profileResponse.value?.first_name.toString()).append(" ")
                .append(profileResponse.value?.last_name.toString())
    }
}