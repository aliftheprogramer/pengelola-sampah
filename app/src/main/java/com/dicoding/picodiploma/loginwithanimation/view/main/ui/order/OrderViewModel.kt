package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampahDao
import kotlinx.coroutines.launch

class OrderViewModel(private val jemputSampahDao: JemputSampahDao) : ViewModel() {

    val allOrders: LiveData<List<JemputSampah>> = jemputSampahDao.getAllOrders()

    fun insertOrder(order: JemputSampah) {
        viewModelScope.launch {
            jemputSampahDao.insert(order)
        }
    }


    fun deleteOrder(order: JemputSampah) {
        viewModelScope.launch {
            jemputSampahDao.delete(order)
        }
        }
}