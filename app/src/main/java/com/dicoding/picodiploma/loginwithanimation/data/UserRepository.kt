package com.dicoding.picodiploma.loginwithanimation.data

import com.dicoding.picodiploma.loginwithanimation.data.database.User
import com.dicoding.picodiploma.loginwithanimation.data.database.UserDao
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampahDao
import com.dicoding.picodiploma.loginwithanimation.data.database.NotificationDao
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val userDao: UserDao,
    private val jemputSampahDao: JemputSampahDao,
    private val notificationDao: NotificationDao
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun saveJemputSampah(jemputSampah: JemputSampah) {
        jemputSampahDao.insert(jemputSampah)
    }

    suspend fun saveNotification(notification: Notification) {
        notificationDao.insert(notification)
    }

    suspend fun getAllNotifications(): List<Notification> {
        return notificationDao.getAllNotifications()
    }

    fun getJemputSampahDao(): JemputSampahDao {
        return jemputSampahDao
    }

    fun getUserDao(): UserDao {
        return userDao
    }

    

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            userDao: UserDao,
            jemputSampahDao: JemputSampahDao,
            notificationDao: NotificationDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, userDao, jemputSampahDao, notificationDao)
            }.also { instance = it }
    }
}