package com.gogame.mycontacts.ui.contact.contactlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gogame.mycontacts.data.db.AppDatabase
import com.gogame.mycontacts.data.db.GroupUsersDao
import com.gogame.mycontacts.data.db.UserDao
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.ui.contact.create.CreateContactListener
import com.gogame.mycontacts.ui.contact.creategroup.GroupListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    var readAllData: LiveData<List<User>>
    private var repository: ContactRepository
    private var groupListRepository: GroupListRepository
    var contactDeleteListener: ContactDeleteListener? = null

    init {
        val userDao : UserDao = AppDatabase.invoke(context = application).getUserDao()
        val groupListDao : GroupUsersDao = AppDatabase.invoke(context = application).getGroupUsers()
        repository = ContactRepository(userDao)
        groupListRepository = GroupListRepository(groupListDao)
        readAllData = repository.readAllData
    }

    fun deleteUser(userId : Int){
        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userId)
            groupListRepository.deleteUser(userId)

            contactDeleteListener?.onSuccess()
        }
    }

    val user by lazy {
        repository.readAllData
    }
}