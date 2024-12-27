package com.dicoding.picodiploma.loginwithanimation.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
            }
        }
        return INSTANCE!!
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS jemput_sampah (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nama TEXT NOT NULL,
                kategori TEXT NOT NULL,
                berat REAL NOT NULL,
                tanggal TEXT NOT NULL,
                alamat TEXT NOT NULL,
                catatan TEXT,
                new_column TEXT,
                paymentMethod TEXT NOT NULL
            )
        """.trimIndent())
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Check if the column already exists
            val cursor = db.query("PRAGMA table_info(jemput_sampah)")
            var columnExists = false
            while (cursor.moveToNext()) {
                val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                if (columnName == "paymentMethod") {
                    columnExists = true
                    break
                }
            }
            cursor.close()

            // Add the column if it does not exist
            if (!columnExists) {
                db.execSQL("ALTER TABLE jemput_sampah ADD COLUMN paymentMethod TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}