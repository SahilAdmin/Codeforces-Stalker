package com.admin_official.codeforcesstalker.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsernameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUsername(username: Username)

    @Query("DELETE FROM user_name WHERE name = :str")
    fun delUsername(str: String)

    @Query("SELECT * FROM user_name ORDER BY name COLLATE NOCASE ASC")
    fun query(): LiveData<List<Username>>

    @Query("SELECT * FROM user_name WHERE name = :str")
    fun search(str: String): LiveData<List<Username>>
}