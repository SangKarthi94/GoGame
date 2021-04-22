package com.gogame.mycontacts.ui.contact.creategroup

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.databinding.GroupListItemBinding
import com.gogame.mycontacts.generated.callback.OnCheckedChangeListener


class GroupSelectAdapter(
    private val contactList: List<Groups>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactListViewHolder(GroupListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactListViewHolder).bind(context, contactList[position])

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            contactList[position].isChecked = isChecked
        }
    }
}


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ContactListViewHolder(private val binding: GroupListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val checkBox = binding.userNameTxt

    @SuppressLint("SimpleDateFormat")
    fun bind(context: Context, contactData: Groups) {
        binding.groupdata = contactData

    }
}




