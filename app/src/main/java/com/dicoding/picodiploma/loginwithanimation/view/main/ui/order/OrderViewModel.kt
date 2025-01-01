package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.database.User
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import kotlinx.coroutines.launch

class OrderViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val allOrders: LiveData<List<JemputSampah>> = userRepository.getJemputSampahDao().getAllOrders()

    fun insertOrder(order: JemputSampah) {
        viewModelScope.launch {
            userRepository.saveJemputSampah(order)
        }
    }

    fun deleteOrder(order: JemputSampah) {
        viewModelScope.launch {
            userRepository.getJemputSampahDao().delete(order)
        }
    }

    fun getUser(email: String, password: String): LiveData<User> {
        val userLiveData = MutableLiveData<User?>()
        val resultLiveData = MediatorLiveData<User>()

        resultLiveData.addSource(userLiveData) { user ->
            resultLiveData.value = user ?: User(id = 0, email = "", password = "", isLogin = 0, ggPoints = 0)
        }

        viewModelScope.launch {
            val user = userRepository.getUserDao().getUser(email, password)
            userLiveData.postValue(user)
        }

        return resultLiveData
    }
}