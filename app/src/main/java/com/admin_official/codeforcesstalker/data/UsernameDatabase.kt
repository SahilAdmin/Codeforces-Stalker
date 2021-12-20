package com.admin_official.codeforcesstalker.data

import android.content.Context
import android.service.autofill.UserData
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val TAG = "De_UsernameDatabase"
@Database(entities=[Username::class], version = 1)
abstract class UsernameDatabase: RoomDatabase() {

    abstract fun userDao(): UsernameDao

    companion object {
        @Volatile
        private var instance: UsernameDatabase? = null

        fun getInstance(context: Context): UsernameDatabase {
            return instance?: synchronized(this) {
                instance?: Room.databaseBuilder(context,
                    UsernameDatabase::class.java,
                    "username_database")
                    .build().also {
                        instance = it
                        Log.d(TAG, "getInstance: instance created ${instance.toString()}")
                    }
            }
        }
    }
}