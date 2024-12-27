package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampahDao

@Database(entities = [User::class, JemputSampah::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun jemputSampahDao(): JemputSampahDao
}