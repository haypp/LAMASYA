package com.lamasya.ui.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.lamasya.R
import com.lamasya.databinding.ActivityChangePasswordBinding
import com.lamasya.ui.auth.PasswordAuth
import com.lamasya.ui.viewmodel.ChangePasswordViewModel
import com.lamasya.util.LoadingDialog
import com.lamasya.util.toast

class ChangePasswordActivity : AppCompatActivity(), PasswordAuth{
    private lateinit var binding: ActivityChangePasswordBinding
    private val changePasswordVM: ChangePasswordViewModel by viewModels()

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changePasswordVM.passwordAuth = this

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tittle_change_password)

        binding.btnChangePasswordDetail.setOnClickListener {
            getPassword()
        }
    }

    override fun onSuccess(message: String) {
        loading.isLoading(false)
        toast(message)
    }

    override fun onFailed(message: String) {
        loading.isLoading(false)
        toast(message)
    }


    private fun getPassword() {
        var validForm = true
        binding.apply {
            CURRENT_PASSWORD = etCurrentPasswordDetail.text.toString()
            val newPassword = etNewPasswordDetail.text.toString()
            val newPassword2 = etNewPasswordDetail2.text.toString()

            if (CURRENT_PASSWORD.length < 6) {
                etCurrentPasswordDetail.error = getString(R.string.invalid_password)
                validForm = false
            }
            if (newPassword.length < 6) {
                etNewPasswordDetail.error = getString(R.string.invalid_password)
                validForm = false
            }
            if (CURRENT_PASSWORD == newPassword) {
                toast(getString(R.string.password_cant_same))
                validForm = false
            }
            if (newPassword != newPassword2) {
                etNewPasswordDetail.error = getString(R.string.password_must_same)
                etNewPasswordDetail2.error = getString(R.string.password_must_same)
                validForm = false
            }
        }
        updatePassword(validForm)
    }

    private fun updatePassword(validForm: Boolean) {
        if(validForm){
            loading.isLoading(true)
            NEW_PASSWORD = binding.etNewPasswordDetail.text.toString()
            changePasswordVM.setPassword(
                CURRENT_PASSWORD,
                NEW_PASSWORD
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) this.finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var CURRENT_PASSWORD = ""
        private var NEW_PASSWORD = ""
    }


}