package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): User?

    @Query("UPDATE user SET ggPoints = :ggPoints WHERE id = :userId")
    suspend fun updateGGPoints(userId: Int, ggPoints: Int)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): User
}