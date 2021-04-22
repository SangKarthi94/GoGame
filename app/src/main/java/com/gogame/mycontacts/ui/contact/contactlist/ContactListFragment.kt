package com.gogame.mycontacts.ui.contact.contactlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogame.mycontacts.R
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.ui.contact.create.ContactListAdapter
import com.gogame.mycontacts.utils.Coroutines
import com.gogame.mycontacts.utils.hide
import com.gogame.mycontacts.utils.show
import com.gogame.mycontacts.utils.toast
import kotlinx.android.synthetic.main.contact_list_fragment.*
import kotlinx.android.synthetic.main.contact_list_fragment.view.*

class ContactListFragment() : Fragment() , ContactDeleteListener{


    private lateinit var viewModel: ContactListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.contact_list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ContactListViewModel::class.java)
        bindUI()

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.createFragment)
        }

        view.group_txt.setOnClickListener {
            findNavController().navigate(R.id.groupListFragment)
        }

        viewModel.contactDeleteListener = this

        return view
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            initRecyclerView(it)
        })
    }


    private fun initRecyclerView(quoteItem: List<User>) {
        val contactAdapter = ContactListAdapter(quoteItem, requireContext(),viewModel)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = contactAdapter
        }
    }

    override fun onSuccess() {
        Coroutines.main {
            context?.toast("Contact Deleted Successfully")
        }
        bindUI()
    }


}