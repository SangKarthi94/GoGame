package com.gogame.mycontacts.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "group_users", indices = [Index(value = ["groupId", "userId"], unique = true)])
class GroupUsers(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var groupId: Int,
    var userId: Int  ) : Parcelable