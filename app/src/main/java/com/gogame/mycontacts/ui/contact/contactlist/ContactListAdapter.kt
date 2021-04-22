package com.gogame.mycontacts.ui.contact.create

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gogame.mycontacts.R
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.databinding.ContactListItemBinding
import com.gogame.mycontacts.ui.contact.contactlist.ContactListViewModel
import com.gogame.mycontacts.utils.Const


class ContactListAdapter(
    private val contactList: List<User>,
    private val context: Context,
    private val viewModel: ContactListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactListViewHolder(ContactListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactListViewHolder).bind(context, contactList[position])

        holder.deleteImg.setOnClickListener {
            viewModel.deleteUser(contactList[position].id)
        }
        if (contactList[position].image!=null){
            holder.contactImg.setImageBitmap(contactList[position].image?.let {
                Const.convertStringToBitmap(
                    it
                )
            })
        }else{
            holder.contactImg.setImageDrawable(context.getDrawable(R.drawable.ic_profile))
        }




    }
}

fun getImage(encodedImage: String): Bitmap {
    val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ContactListViewHolder(private val binding: ContactListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val deleteImg = binding.deleteImg
    val contactImg = binding.contactImg

    @SuppressLint("SimpleDateFormat")
    fun bind(context: Context, contactData: User) {
        binding.userdata = contactData

    }
}




