package com.admin_official.codeforcesstalker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_name")
data class Username(@PrimaryKey val name: String)