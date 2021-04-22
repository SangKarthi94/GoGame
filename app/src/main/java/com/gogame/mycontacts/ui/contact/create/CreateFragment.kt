package com.gogame.mycontacts.ui.contact.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gogame.mycontacts.R
import com.gogame.mycontacts.databinding.FragmentCreateBinding
import com.gogame.mycontacts.ui.contact.creategroup.GroupUsersListModel
import com.gogame.mycontacts.ui.contact.creategroup.GroupViewModel
import com.gogame.mycontacts.utils.BaseFragment
import com.gogame.mycontacts.utils.Coroutines
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class CreateFragment : BaseFragment(), CreateContactListener {

    lateinit var viewModel: CreateContactViewModel
    lateinit var groupModel: GroupViewModel
    lateinit var groupUserModel: GroupUsersListModel
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    var userId = ArrayList<Int>()

    //Bitmap photo;
    var photo: String = ""
    lateinit var theImage: Bitmap
    lateinit var user_img: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCreateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)
        viewModel = ViewModelProvider(this).get(CreateContactViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        user_img = binding.userImage

        groupModel = (activity?.run {
            ViewModelProvider(this)[GroupViewModel::class.java]
        } ?: throw Exception("Invalid Activity"))

        groupUserModel = (activity?.run {
            ViewModelProvider(this)[GroupUsersListModel::class.java]
        } ?: throw Exception("Invalid Activity"))

        groupModel.arrayData.observe(viewLifecycleOwner, { item ->
            var btn_name = ""
            userId.clear()
            for (group in item) {
                Log.e("data", "" + group.groupName)
                if (group.isChecked) {
                    btn_name = btn_name + group.groupName+ ",\t"
                    userId.add(group.groupId)
                }
                binding.addGroupBtn.text = btn_name
            }
        })

        binding.userImage.setOnClickListener {
            if (checkAndRequestPermissions()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                checkAndRequestPermissions()
            }
        }

        viewModel.createContactListener = this

        return binding.root
    }

    override fun onStarted() {
        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(data: Long) {
        createGroupUsers(data)
    }

    private fun createGroupUsers(data: Long) {

        for (group in userId) {
            Log.e("data", "" + group)
            groupUserModel.onCreateGroup(groupId = group, data.toInt())
        }
        //UI Implementation on Main thread
        Coroutines.main {
            Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.contactListFragment)
        }

    }

    override fun onNavGroup() {
        findNavController().navigate(R.id.groupFragment)
    }

    override fun onFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                theImage = data.extras?.get("data") as Bitmap
                photo = getEncodedString(theImage)
                Log.e("pic",photo)
                user_img.setImageBitmap(convertStringToBitmap(photo))
                viewModel.image = photo
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }



}