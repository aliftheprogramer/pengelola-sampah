package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.database.DatabaseBuilder
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = DatabaseBuilder.getInstance(context)
        val userDao = database.userDao()
        val jemputSampahDao = database.jemputSampahDao()
        val notificationDao = database.notificationDao() // Add this line
        return UserRepository.getInstance(pref, userDao, jemputSampahDao, notificationDao)
    }
}