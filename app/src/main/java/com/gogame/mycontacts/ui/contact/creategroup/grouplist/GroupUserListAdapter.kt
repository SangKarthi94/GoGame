package com.gogame.mycontacts.ui.contact.creategroup.grouplist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.databinding.GroupUserListBinding

@Suppress("CAST_NEVER_SUCCEEDS")
class GroupUserListAdapter(
    private val contactList: List<User>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GroupUserListViewHolder(GroupUserListBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GroupUserListViewHolder).bind(context, contactList[position])
    }
}


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GroupUserListViewHolder(private val binding: GroupUserListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(context: Context,contactData: User) {
        binding.groupUserData = contactData

    }
}




