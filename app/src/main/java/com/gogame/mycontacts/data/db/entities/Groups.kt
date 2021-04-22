package com.gogame.mycontacts.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "groups", indices = [Index(value = ["groupName"], unique = true)])
data class Groups (
    @PrimaryKey(autoGenerate = true)
    var groupId: Int = 0,
    var groupName: String,
    var isChecked : Boolean = false) : Parcelable