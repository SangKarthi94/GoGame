package com.gogame.mycontacts.ui.contact.creategroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gogame.mycontacts.data.db.AppDatabase
import com.gogame.mycontacts.data.db.GroupDao
import com.gogame.mycontacts.data.db.GroupUsersDao
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application) : AndroidViewModel(application) {

    var groupId: Int = 0
    var isChecked : Boolean = false
    var groupName: String = ""

    val arrayData = MutableLiveData<MutableList<Groups>>()

    var createGroupListener : CreateGroupListener? = null

    var repository : GroupRepository
    var groupListRepository : GroupListRepository

    init {
        val groupDao: GroupDao = AppDatabase.invoke(context = application).getGroupDao()
        val groupListDao: GroupUsersDao = AppDatabase.invoke(context = application).getGroupUsers()
        repository = GroupRepository(groupDao)
        groupListRepository = GroupListRepository(groupListDao)
    }


    fun onCreateGroup(groupName : String) {
        createGroupListener?.onStarted()
        if (groupName.isNullOrEmpty()) {
            createGroupListener?.onFailure("Invalid Data")
            return
        }

        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            val group = Groups(0, groupName, false)
            val data = repository.addGroup(group)

            if (data.toInt() > 1){
                createGroupListener?.onSuccess()
            }else{
                createGroupListener?.onFailure("Group Name already available")
            }
        }
    }

    fun deleteGroup(groupId : Int){
        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroup(groupId)
            groupListRepository.deleteGroupUser(groupId)

            createGroupListener?.onRemove()
        }
    }

    fun deleteGroupUsers(groupId : Int){
        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            groupListRepository.deleteGroupUser(groupId)
            createGroupListener?.onRemove()
        }
    }

    val groups by lazy {
        repository.getGroups
    }


    fun getSelectedUsers(group : Long) : LiveData<List<User>>{
        val data = repository.getSelectedUsers(group)
        return data
    }

    private fun checkData() : Boolean{
        return !(groupName.isNullOrEmpty())
    }
}



