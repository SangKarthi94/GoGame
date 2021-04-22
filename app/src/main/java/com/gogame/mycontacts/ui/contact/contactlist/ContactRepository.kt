package com.gogame.mycontacts.ui.contact.contactlist

import androidx.lifecycle.LiveData
import com.gogame.mycontacts.data.db.UserDao
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.utils.Coroutines
import java.time.LocalDateTime

//abstract access to multiple data sources
//Not part of architecture component
class ContactRepository(private val userDao: UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user : User) : Long{
        return userDao.addUser(user)

    }

    suspend fun deleteUser(userId: Int) {
        userDao.deleteById(userId)
    }
}