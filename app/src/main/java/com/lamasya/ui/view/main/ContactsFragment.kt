package com.lamasya.ui.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamasya.data.remote.contacts.ContactsResponse
import com.lamasya.databinding.FragmentContactsBinding
import com.lamasya.ui.adapter.ContactsAdapter
import com.lamasya.ui.viewmodel.ContactsViewModel
import com.lamasya.util.logE

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private val citemList = ArrayList<ContactsResponse>()
    private var hideType = true
    private val contactsVM: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMyProductItems.setHasFixedSize(true)
//        showRecyclerList()
        var paramC = "rumah_sakit"
        addItems(paramC)
        hideType = false
        binding.btnRumahsakit.setOnClickListener {
            paramC = "rumah_sakit"
            hideType = false
            addItems(paramC)
        }
        binding.btnPemadam.setOnClickListener {
            paramC = "polisi"
            hideType = true
            addItems(paramC)
        }
        binding.btnPolisi.setOnClickListener {
            paramC = "polisi"
            hideType = true
            addItems(paramC)
        }
    }

    private fun addItems(paramC: String) {
        citemList.clear()
        contactsVM.getRS(paramC)
        contactsVM.dataRS.observe(viewLifecycleOwner) {
            citemList.addAll(listOf(it))
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvMyProductItems.layoutManager = LinearLayoutManager(binding.root.context)
        val listMenuAdapter = ContactsAdapter(citemList, hideType)
        binding.rvMyProductItems.adapter = listMenuAdapter
    }
}

private fun <E> ArrayList<E>.addAll(elements: List<E?>) {
    for (element in elements) {
        if (element != null) {
            this.add(element)
        }
    }
}
