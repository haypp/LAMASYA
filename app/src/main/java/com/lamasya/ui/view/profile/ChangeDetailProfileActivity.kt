package com.lamasya.ui.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.lamasya.R
import com.lamasya.data.remote.profile.ProfileRequest
import com.lamasya.data.remote.profile.ProfileResponse
import com.lamasya.databinding.ActivityChangeDetailProfileBinding
import com.lamasya.ui.auth.ProfileAuth
import com.lamasya.ui.view.main.MainActivity
import com.lamasya.ui.viewmodel.ProfileViewModel
import com.lamasya.util.toast

class ChangeDetailProfileActivity : AppCompatActivity(), ProfileAuth {

    private lateinit var binding: ActivityChangeDetailProfileBinding
    private val profileVM: ProfileViewModel by viewModels()
    private val currentUID = MainActivity.CURRENT_UID

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChangeDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileVM.profileAuth = this

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =
            StringBuilder("Ubah ").append(intent.getStringExtra(EXTRA_ITEM).toString())
        getProfileData()
        setFormVisible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.save_button_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
                return true
            }
            R.id.menuSaveData -> {
                this.finish()
                getValue()
                updateProfile()
                return true
            }
        }
        return true
    }

    override fun onSuccess(profileResponse: LiveData<ProfileResponse>) {
        binding.apply {
            val dataProfile = profileResponse.value
            val dataGender = dataProfile?.gender.toString()
            etEditFname.text = Editable.Factory.getInstance().newEditable(dataProfile?.first_name)
            etEditLname.text = Editable.Factory.getInstance().newEditable(dataProfile?.last_name)
            etEditEmail.text = Editable.Factory.getInstance().newEditable(dataProfile?.email)
            etEditPhone.text = Editable.Factory.getInstance().newEditable(dataProfile?.phone)
            etEditAge.text = Editable.Factory.getInstance().newEditable(dataProfile?.age.toString())

            if (dataGender == "Laki - Laki") {
                rgUpdateGender.check(rbUpdateMale.id)
            } else if (dataGender == "Perempuan") {
                rgUpdateGender.check(rbUpdateFemale.id)
            }
        }
    }

    private fun updateProfile() {
        var validForm = true
        binding.apply {
            if (FNAME == "") {
                etEditFname.error = getString(R.string.please_fill_field)
                validForm = false
            }
            if (LNAME == "") {
                etEditLname.error = getString(R.string.please_fill_field)
                validForm = false
            }
            if (PHONE == "") {
                etEditPhone.error = getString(R.string.please_fill_field)
                validForm = false
            }
            if (GENDER == "") {
                toast(getString(R.string.gender_option_null))
                validForm = false
            }
            if (AGE == 0) {
                etEditAge.error = getString(R.string.please_fill_field)
                validForm = false
            }
            updateToRemote(validForm)
        }
    }

    private fun updateToRemote(validForm: Boolean) {
        if (validForm) {
            profileVM.updateProfile(
                ProfileRequest(
                    currentUID,
                    FNAME,
                    LNAME,
                    PHONE,
                    GENDER,
                    AGE
                )
            )
            toast(getString(R.string.update_success))
            finish()
        }
    }

    private fun getProfileData() {
        profileVM.getProfile(currentUID)
    }

    private fun getValue() {
        binding.apply {
            FNAME = etEditFname.text.toString()
            LNAME = etEditLname.text.toString()
            PHONE = etEditPhone.text.toString()

            AGE = if (etEditAge.text.isNullOrEmpty()) {
                0
            } else {
                Integer.valueOf(etEditAge.text.toString())
            }

            GENDER = if (rbUpdateMale.isChecked) {
                getString(R.string.male)
            } else if (rbUpdateFemale.isChecked) {
                getString(R.string.female)
            } else {
                ""
            }

        }
    }

    private fun setFormInvisible() {
        binding.apply {
            FnametextUpdate.visibility = View.GONE
            LnametextUpdate.visibility = View.GONE
            EmailTextUpdate.visibility = View.GONE
            PhoneTextUpdate.visibility = View.GONE
            tvUpdateGenderDesc.visibility = View.GONE
            rgUpdateGender.visibility = View.GONE
            AgeTextUpdate.visibility = View.GONE
        }
    }

    private fun setFormVisible() {
        val item = intent.getStringExtra(EXTRA_ITEM).toString()
        setFormInvisible()
        binding.apply {
            when (item) {
                "Nama Lengkap" -> {
                    FnametextUpdate.visibility = View.VISIBLE
                    LnametextUpdate.visibility = View.VISIBLE
                }
                "Email" -> {
                    EmailTextUpdate.visibility = View.VISIBLE
                }
                "No Telephone" -> {
                    PhoneTextUpdate.visibility = View.VISIBLE
                }
                "Jenis Kelamin" -> {
                    tvUpdateGenderDesc.visibility = View.VISIBLE
                    rgUpdateGender.visibility = View.VISIBLE
                }
                "Umur" -> {
                    AgeTextUpdate.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        var EXTRA_ITEM = "EXTRA_ITEM"
        private var FNAME = ""
        private var LNAME = ""
        private var PHONE = ""
        private var AGE = 0
        private var GENDER = ""
    }
}
