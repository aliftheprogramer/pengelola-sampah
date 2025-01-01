package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val isLogin: Int=   1, // Ubah ke Int (0 atau 1)
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val password: String,
    val ggPoints: Int
)