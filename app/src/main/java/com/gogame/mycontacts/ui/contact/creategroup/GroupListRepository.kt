package com.gogame.mycontacts.ui.contact.creategroup

import androidx.lifecycle.LiveData
import com.gogame.mycontacts.data.db.GroupUsersDao
import com.gogame.mycontacts.data.db.entities.GroupUsers

//abstract access to multiple data sources
//Not part of architecture component
class GroupListRepository(private val groupDao: GroupUsersDao) {

    val getGroupUsers: LiveData<List<GroupUsers>> = groupDao.getGroupUsers()

    suspend fun addGroupUsers(group: GroupUsers): Long {
        val data = groupDao.addGroupUser(group)
        return data
    }

    suspend fun deleteUser(userId: Int) {
        groupDao.deleteUser(userId)
    }

    suspend fun deleteGroupUser(groupId: Int) {
        groupDao.deleteGroupUser(groupId)
    }

}