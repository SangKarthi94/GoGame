package com.gogame.mycontacts.ui.contact.creategroup.grouplist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gogame.mycontacts.R
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.databinding.GroupListBinding
import com.gogame.mycontacts.ui.contact.creategroup.GroupViewModel
import com.gogame.mycontacts.utils.Const


class GroupListAdapter(
    private val contactList: List<Groups>,
    private val context: Context,
    private val viewModel: GroupViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactListViewHolder(GroupListBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactListViewHolder).bind(context, contactList[position])

        holder.userNameTxt.setOnClickListener {
            Const.groupId = contactList[position].groupId
            val bundle = bundleOf( "groupId" to contactList[position].groupId.toString())
            it.findNavController().navigate(R.id.groupUserListFragment, bundle)
        }

        holder.deleteImg.setOnClickListener {
            viewModel.deleteGroup(contactList[position].groupId)
        }
    }
}


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ContactListViewHolder(private val binding: GroupListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val userNameTxt = binding.userNameTxt
    val deleteImg = binding.deleteImg

    @SuppressLint("SimpleDateFormat")
    fun bind(context: Context, contactData: Groups) {
        binding.groupdata = contactData
    }
}




