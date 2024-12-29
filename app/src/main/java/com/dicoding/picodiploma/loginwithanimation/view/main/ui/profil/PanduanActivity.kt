package com.dicoding.picodiploma.loginwithanimation.view.main.ui.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityPanduanBinding

class PanduanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanduanBinding
    private lateinit var adapter: PanduanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        binding.recyclerViewPanduan.layoutManager = LinearLayoutManager(this)

        // Data contoh untuk panduan
        val panduanList = arrayOf(
            "Registrasi",
            "Pickup Sampah",
            "Drop Off Sampah",
            "Company dan Event",
            "Menjadi Mitra",
            "Jenis dan Harga Sampah"
        )

        // Inisialisasi adapter dan set ke RecyclerView
        adapter = PanduanAdapter(panduanList)
        binding.recyclerViewPanduan.adapter = adapter
    }
}