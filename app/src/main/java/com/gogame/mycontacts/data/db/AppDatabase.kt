package com.gogame.mycontacts.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gogame.mycontacts.data.db.entities.GroupUsers
import com.gogame.mycontacts.data.db.entities.Groups
import com.gogame.mycontacts.data.db.entities.User

@Database(
    entities = [User::class, Groups::class, GroupUsers::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getGroupDao(): GroupDao
    abstract fun getGroupUsers(): GroupUsersDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()


        fun getDataBase(context: Context): AppDatabase {

            //Instance already exits we retur same instance else create instance

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "user_database").build()

                return instance
            }


        }
    }
}