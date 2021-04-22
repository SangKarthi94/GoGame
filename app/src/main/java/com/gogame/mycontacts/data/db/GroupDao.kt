package com.gogame.mycontacts.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User

@Dao
interface GroupDao{

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addGroup(user: Groups) : Long

    //For Reading the datas
    @Query("SELECT * FROM groups ORDER BY groupId ASC")
    fun getGroups() : LiveData<List<Groups>>

    @Query("SELECT * FROM group_users JOIN user_table ON user_table.id = group_users.userId WHERE group_users.groupId =:group")
    fun getSelectedGroup(group: Long): LiveData<List<User>>


    @Query("DELETE FROM groups WHERE groupId = :id")
    fun deleteById(id: Int)
}