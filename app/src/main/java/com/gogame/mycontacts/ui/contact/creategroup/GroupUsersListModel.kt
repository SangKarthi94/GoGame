package com.gogame.mycontacts.ui.contact.creategroup

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gogame.mycontacts.data.db.AppDatabase
import com.gogame.mycontacts.data.db.GroupDao
import com.gogame.mycontacts.data.db.GroupUsersDao
import com.gogame.mycontacts.data.db.entities.GroupUsers
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupUsersListModel(application: Application) : AndroidViewModel(application) {

    var isChecked : Boolean = false

    var createGroupListener : CreateGroupListener? = null

    var repository : GroupListRepository

    init {
        val groupDao: GroupUsersDao = AppDatabase.invoke(context = application).getGroupUsers()
        repository = GroupListRepository(groupDao)
    }


    fun onCreateGroup(groupId : Int, userId : Int) {
        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            val group = GroupUsers(0,groupId, userId)
            val data = repository.addGroupUsers(group)

            if (data.toInt() > 1){
                createGroupListener?.onSuccess()
            }
        }
    }

    val groups by lazy {
        repository.getGroupUsers
    }
}



