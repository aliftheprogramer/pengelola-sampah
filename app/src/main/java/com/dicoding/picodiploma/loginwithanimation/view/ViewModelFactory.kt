package com.dicoding.picodiploma.loginwithanimation.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampahDao
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignupViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.ui.jemputsampah.JemputSampahViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.ui.order.OrderViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val jemputSampahDao: JemputSampahDao
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(JemputSampahViewModel::class.java) -> {
                JemputSampahViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(jemputSampahDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val jemputSampahDao = Injection.provideDatabase(context)
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    jemputSampahDao
                )
            }.also { instance = it }
    }
}