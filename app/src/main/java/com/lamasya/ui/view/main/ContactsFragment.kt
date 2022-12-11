package com.lamasya.ui.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lamasya.data.remote.contacts.ContactsResponse
import com.lamasya.databinding.FragmentContactsBinding
import com.lamasya.ui.adapter.ContactsAdapter
import com.lamasya.util.logE

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private val citemList = ArrayList<ContactsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItems(PARAMC)

        binding.btnRumahsakit.setOnClickListener {
            PARAMC = "rumah_sakit"
            addItems(PARAMC)
        }
        binding.btnPemadam.setOnClickListener {
            PARAMC = "pemadam_kebakaran"
            addItems(PARAMC)
        }
        binding.btnPolisi.setOnClickListener {
            PARAMC = "polisi"
            addItems(PARAMC)
        }
        binding.searchViewContacts.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchItems(PARAMC, newText)
                }
                return true
            }
        })
    }
    private fun addItems(paramC: String) {
        citemList.clear()
        Firebase.firestore.collection(paramC).get()
            .addOnSuccessListener {
                context?.logE("itemV $paramC")
                for (document in it.documents) {
                    citemList.add(
                        ContactsResponse(
                            document.data!!["nama"].toString(),
                            document.data!!["alamat"].toString(),
                            document.data!!["no_telepon"].toString(),
                            document.data!!["gambar"].toString(),
                            document.data!!["link_maps"].toString(),
                            document.data!!["jenis"].toString()
                        )
                    )
                }
                showRecyclerList()
            }
    }
    private fun searchItems(paramC: String, query: String) {
        citemList.clear()
        Firebase.firestore.collection(paramC).orderBy("nama").startAt(query)
            .endAt(query + "\uf8ff").limit(10)
            .get()
            .addOnSuccessListener {
                context?.logE("itemV $paramC $query")
                for (document in it.documents) {
                    citemList.add(
                        ContactsResponse(
                            document.data!!["nama"].toString(),
                            document.data!!["alamat"].toString(),
                            document.data!!["no_telepon"].toString(),
                            document.data!!["gambar"].toString(),
                            document.data!!["link_maps"].toString(),
                            document.data!!["jenis"].toString()
                        )
                    )
                }
                showRecyclerList()
            }
    }

    private fun showRecyclerList() {
        binding.apply {
            rvMyProductItems.layoutManager = LinearLayoutManager(binding.root.context)
            val listMenuAdapter = ContactsAdapter(citemList)
            rvMyProductItems.adapter = listMenuAdapter
            rvMyProductItems.setHasFixedSize(true)
        }
    }

    companion object {
        var PARAMC = "rumah_sakit"
    }
}
