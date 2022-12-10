package com.lamasya.util

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import com.lamasya.R

class LoadingDialog(private val activity: Activity) {
    private lateinit var dialog: AlertDialog

    fun isLoading(loading: Boolean) {
        val inflater = activity.layoutInflater.inflate(R.layout.activity_progress_bar, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(inflater)
        builder.setCancelable(false)

        if (loading) {
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }
}