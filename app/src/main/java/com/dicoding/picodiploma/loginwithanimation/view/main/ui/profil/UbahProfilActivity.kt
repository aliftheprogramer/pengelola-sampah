package com.dicoding.picodiploma.loginwithanimation.view.main.ui.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityUbahProfilBinding

class UbahProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUbahProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Implement your profile update logic here
    }
}