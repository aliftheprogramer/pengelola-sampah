package com.dicoding.picodiploma.loginwithanimation.view.main.ui.jemputsampah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import kotlinx.coroutines.launch

class JemputSampahViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun saveJemputSampah(jemputSampah: JemputSampah){
        viewModelScope.launch {
            repository.saveJemputSampah(jemputSampah)
        }
    }
}