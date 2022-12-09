package com.lamasya.ui.auth

interface PasswordAuth {
    fun onSuccess(message: String)
    fun onFailed(message: String)
}