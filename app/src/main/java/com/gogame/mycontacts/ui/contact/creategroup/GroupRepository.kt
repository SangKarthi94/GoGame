package com.gogame.mycontacts.ui.contact.creategroup

import androidx.lifecycle.LiveData
import com.gogame.mycontacts.data.db.GroupDao
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User

//abstract access to multiple data sources
//Not part of architecture component
class GroupRepository(private val groupDao: GroupDao) {

    val getGroups : LiveData<List<Groups>> = groupDao.getGroups()

    suspend fun addGroup(group : Groups) : Long{
        val data = groupDao.addGroup(group)
        return data
    }

    suspend fun deleteGroup(id : Int) {
        groupDao.deleteById(id)
    }

    fun getSelectedUsers(group : Long) : LiveData<List<User>>{
        val data = groupDao.getSelectedGroup(group)
        return data
    }



}