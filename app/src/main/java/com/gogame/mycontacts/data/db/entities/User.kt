package com.gogame.mycontacts.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "user_table", indices = [Index(value = ["name", "phone"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var email: String? = null,
    var phone: String,
    var image: String? = null,
    var is_fav: Boolean = false
) : Parcelable