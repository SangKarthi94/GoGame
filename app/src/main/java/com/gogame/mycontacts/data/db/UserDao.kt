package com.gogame.mycontacts.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gogame.mycontacts.data.db.entities.User

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(user: User)

    //For Reading the datas
    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun readAllData() : LiveData<List<User>>


    @Query("SELECT * FROM user_table")
    fun getUsers() : LiveData<List<User>>

    //For Inserting the data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User) : Long

    @Query("DELETE FROM user_table WHERE id = :id")
    fun deleteById(id: Int)

}