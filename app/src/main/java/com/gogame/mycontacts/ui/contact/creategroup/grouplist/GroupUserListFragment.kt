package com.gogame.mycontacts.ui.contact.creategroup.grouplist

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogame.mycontacts.R
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.ui.contact.creategroup.CreateGroupListener
import com.gogame.mycontacts.ui.contact.creategroup.GroupViewModel
import com.gogame.mycontacts.utils.Const
import com.gogame.mycontacts.utils.Coroutines
import com.gogame.mycontacts.utils.hide
import com.gogame.mycontacts.utils.show
import kotlinx.android.synthetic.main.fragment_group_user_list.*
import kotlinx.android.synthetic.main.fragment_group_user_list.view.*

class GroupUserListFragment : Fragment(), CreateGroupListener {

    companion object {
        fun newInstance() = GroupUserListFragment()
    }

    private lateinit var viewModel: GroupViewModel
    lateinit var mAlertDialog: AlertDialog

    var contactList: List<User> = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_user_list, container, false)

        viewModel = (activity?.run {
            ViewModelProvider(this)[GroupViewModel::class.java]
        } ?: throw Exception("Invalid Activity"))

        bindUI()
        view.remove_users.setOnClickListener {
            viewModel.deleteGroupUsers(Const.groupId)
        }
        return view
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.getSelectedUsers(Const.groupId.toLong()).observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            contactList = it
            initRecyclerView(contactList)
        })
    }


    private fun initRecyclerView(quoteItem: List<User>) {

        val contactAdapter = GroupUserListAdapter(quoteItem, requireContext())
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = contactAdapter
        }

    }

    override fun onStarted() {
    }

    override fun onSuccess() {
        bindUI()
    }

    override fun onFailure(message: String) {
    }

    override fun onRemove() {
        bindUI()
    }

    private fun showUpgradePrompt() {

        val inflater = requireActivity().layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.alert_group_name, null)
        val mBuilder = context.let {
            AlertDialog.Builder(it)
                .setView(alertLayout)
        }
        val grp_name_edt = alertLayout.findViewById<View>(R.id.grp_name_edt) as AppCompatEditText
        val cancel_txt = alertLayout.findViewById<View>(R.id.cancel_txt) as AppCompatTextView
        val upgrade_txt = alertLayout.findViewById<View>(R.id.upgrade_txt) as AppCompatTextView
        val alert: AlertDialog.Builder =
            AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
        alert.setView(alertLayout)
        alert.setCancelable(true)

        upgrade_txt.setOnClickListener {
            if (grp_name_edt.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "Enter Valid Group Name", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.onCreateGroup(grp_name_edt.text.toString())
                mAlertDialog.dismiss()
            }

        }

        cancel_txt.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mAlertDialog = mBuilder.show()
        mAlertDialog.setCancelable(false)
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(this.resources.getColor(android.R.color.transparent)))
        val dialogWindow = mAlertDialog.window
        dialogWindow?.setGravity(Gravity.CENTER)
    }

}