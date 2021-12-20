package com.admin_official.codeforcesstalker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsernameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUsername(username: Username)

    @Delete
    fun deleteUsername(username: Username)

    @Query("SELECT * FROM user_name ORDER BY name ASC")
    fun query(): LiveData<List<Username>>

    @Query("SELECT * FROM user_name WHERE name LIKE :str")
    fun search(str: String): LiveData<List<Username>>
}