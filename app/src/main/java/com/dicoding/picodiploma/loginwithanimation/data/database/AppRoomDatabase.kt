package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.loginwithanimation.data.Notification
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampahDao

@Database(entities = [User::class, JemputSampah::class, Notification::class], version = 4)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun jemputSampahDao(): JemputSampahDao
    abstract fun notificationDao(): NotificationDao
}