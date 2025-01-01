package com.dicoding.picodiploma.loginwithanimation.view.main.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.Notification
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.launch

class NotificationsViewModel(private val repository: UserRepository) : ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    fun loadNotifications() {
        viewModelScope.launch {
            _notifications.value = repository.getAllNotifications()
        }
    }
}