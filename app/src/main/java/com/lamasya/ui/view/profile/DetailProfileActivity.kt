package com.lamasya.ui.view.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.lamasya.R
import com.lamasya.data.model.MenuDetailProfileModel
import com.lamasya.data.remote.profile.ProfileResponse
import com.lamasya.databinding.ActivityDetailProfileBinding
import com.lamasya.ui.adapter.DetailProfileMenuAdapter
import com.lamasya.ui.auth.ProfileAuth
import com.lamasya.ui.view.main.MainActivity
import com.lamasya.ui.viewmodel.ProfileViewModel
import com.lamasya.util.intent
import com.lamasya.util.logE
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class DetailProfileActivity : AppCompatActivity(), ProfileAuth,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityDetailProfileBinding

    private val profileVM: ProfileViewModel by viewModels()
    private val itemList = ArrayList<MenuDetailProfileModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileVM.profileAuth = this
        onRefresh()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tittle_detail_profile)

        binding.apply {
            swpRefreshDetailProfile.setOnRefreshListener {
                onRefresh()
            }

            btnChangeProfile.setOnClickListener {
                setPhotoProfile()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) this.finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(profileResponse: LiveData<ProfileResponse>) {
        addItem(profileResponse)
    }

    override fun onRefresh() {
        binding.apply {
            swpRefreshDetailProfile.isRefreshing = true
            Timer().schedule(2000) {
                swpRefreshDetailProfile.isRefreshing = false
                getProfileData()
            }
        }
    }

    private fun getProfileData() {
        val currentUID = MainActivity.CURRENT_UID
        profileVM.getProfile(currentUID)
    }

    private fun setPhotoProfile() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun addItem(profileResponse: LiveData<ProfileResponse>) {
        val dataProfile = profileResponse.value
        if(dataProfile?.photo != null){
            Glide.with(this)
                .load(dataProfile.photo)
                .circleCrop()
                .into(binding.imvDetailInfoPhoto)
        }

        val dataListItem = arrayOf(
            StringBuilder(dataProfile?.first_name.toString()).append(" ")
                .append(dataProfile?.last_name.toString()).toString(),
            StringBuilder(dataProfile?.email.toString()).toString(),
            StringBuilder(dataProfile?.phone.toString()).toString(),
            dataProfile?.gender.toString(),
            dataProfile?.age.toString()
        )

        itemList.clear()

        val listSize = titleListItem.size - 1
        for (i in 0..listSize) {
            itemList.add(
                MenuDetailProfileModel(
                    titleListItem[i],
                    dataListItem[i]
                )
            )
            showRecyclerList()
        }
        logE("ara list $itemList")

    }

    private fun showRecyclerList() {
        binding.apply {
            rvListDetailProfile.layoutManager = LinearLayoutManager(root.context)
            val mListDetailProfileAdapter = DetailProfileMenuAdapter(itemList)
            rvListDetailProfile.adapter = mListDetailProfileAdapter
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            IMAGE_URI = selectedImg
            intent(DetailPhotoActivity::class.java)
        }
    }

    companion object {
        private val titleListItem =
            arrayOf("Nama Lengkap", "Email", "No Telephone", "Jenis Kelamin", "Umur")
        lateinit var IMAGE_URI: Uri
    }

}