package com.lamasya.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.lamasya.data.remote.story.DetailStoryResponse
import com.lamasya.ui.auth.DetailStoryAuth

class DetailStoryViewModel : ViewModel() {
    private var mDetailStoryResponse = MutableLiveData<DetailStoryResponse>()
    val liveDetailStoryResponse: LiveData<DetailStoryResponse> = mDetailStoryResponse

    var detailStoryAuth: DetailStoryAuth? = null

    private var UID = ""
    private var STORY_ID = ""
    private var POST_NAME = ""
    private var POST_PROFILE_PICT = ""
    private var CREATED_AT = ""
    private var SITUATION = ""
    private var DESCRIPTION = ""
    private var STORY_PICT = ""

    fun getDetailStory(storyID: String, authorID: String) {
        STORY_ID = storyID
        UID = authorID
        getStory(STORY_ID)
        Log.e(this@DetailStoryViewModel.toString(), "get Story VM: $STORY_ID")
    }

    private fun getStory(storyID: String) {
        FirebaseFirestore.getInstance().collection("stories").document(storyID)
            .get().addOnSuccessListener {
                Log.e(this@DetailStoryViewModel.toString(), "getStory: $STORY_ID")
                Log.e(this@DetailStoryViewModel.toString(), "getUID 1: $UID")
                CREATED_AT = it.data!!["created_at"].toString()
                SITUATION = it.data!!["situation"].toString()
                DESCRIPTION = it.data!!["desc"].toString()
                STORY_PICT = it.data!!["pic"].toString()
                getDetailProfile(UID)
            }
    }

    private fun getDetailProfile(uId: String) {
        Log.e(this@DetailStoryViewModel.toString(), "getUID 2: $uId")
        Log.e(this@DetailStoryViewModel.toString(), "getUID 3: $UID")
        FirebaseFirestore.getInstance().collection("detail_user").document(uId).get()
            .addOnSuccessListener {
                POST_NAME = StringBuilder(it.data!!["first_name"].toString()).append(" ")
                    .append(it.data!!["last_name"].toString()).toString()
                POST_PROFILE_PICT = it.data!!["profile_pict"].toString()
                mDetailStoryResponse.value = DetailStoryResponse(
                    STORY_ID,
                    UID,
                    POST_PROFILE_PICT,
                    POST_NAME,
                    SITUATION,
                    CREATED_AT,
                    DESCRIPTION,
                    STORY_PICT
                )
                detailStoryAuth?.onSuccess(liveDetailStoryResponse)

            }
    }
}