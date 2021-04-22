package com.gogame.mycontacts.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gogame.mycontacts.data.db.entities.GroupUsers
import com.gogame.mycontacts.data.db.entities.User

@Dao
interface GroupUsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGroupUser(user: GroupUsers): Long

    //For Reading the datas
    @Query("SELECT * FROM group_users ORDER BY groupId ASC")
    fun getGroupUsers(): LiveData<List<GroupUsers>>


    //    @Query("SELECT user_table*. , Bar.* FROM Foo INNER JOIN Bar ON Foo.bar = Bar.id")
    @Query("SELECT * FROM user_table")
    fun getSelectedGroup(): LiveData<List<User>>

    @Query("DELETE FROM group_users WHERE userId = :id")
    fun deleteUser(id: Int)

    @Query("DELETE FROM group_users WHERE groupId = :id")
    fun deleteGroupUser(id: Int)
}