package com.lamasya.ui.auth

import androidx.lifecycle.LiveData
import com.lamasya.data.remote.story.DetailStoryResponse

interface DetailStoryAuth {
    fun onSuccess(storyResponse: LiveData<DetailStoryResponse>)
}