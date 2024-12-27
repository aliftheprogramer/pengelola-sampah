package com.dicoding.picodiploma.loginwithanimation.data.database.datasampah

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jemput_sampah")
data class JemputSampah(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val kategori: String,
    val berat: Double,
    val tanggal: String,
    val alamat: String,
    val catatan: String?,
    val new_column: String?,
    val paymentMethod: String
)