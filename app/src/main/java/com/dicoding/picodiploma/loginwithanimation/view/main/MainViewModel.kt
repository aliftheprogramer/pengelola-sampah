package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userSession = userRepository.getSession().asLiveData()

    suspend fun logout() {
        userRepository.logout()
    }
}