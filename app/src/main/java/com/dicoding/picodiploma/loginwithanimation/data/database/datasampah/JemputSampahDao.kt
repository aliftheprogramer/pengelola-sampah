package com.dicoding.picodiploma.loginwithanimation.data.database.datasampah

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JemputSampahDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jemputSampah: JemputSampah)

    @Query("SELECT * FROM jemput_sampah")
    fun getAllOrders(): LiveData<List<JemputSampah>>

    @Delete
    suspend fun delete(jemputSampah: JemputSampah)
}