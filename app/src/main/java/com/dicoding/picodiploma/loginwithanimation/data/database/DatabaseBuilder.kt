package com.dicoding.picodiploma.loginwithanimation.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }
        return INSTANCE!!
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Create the jemput_sampah table if it does not exist
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS jemput_sampah (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nama TEXT NOT NULL,
                kategori TEXT NOT NULL,
                berat REAL NOT NULL,
                tanggal TEXT NOT NULL,
                alamat TEXT NOT NULL,
                catatan TEXT,
                new_column TEXT
            )
        """.trimIndent())

            // Add the isLogin column to the user table if it does not exist
            val cursor = db.query("PRAGMA table_info(user)")
            var isLoginColumnExists = false
            while (cursor.moveToNext()) {
                val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                if (columnName == "isLogin") {
                    isLoginColumnExists = true
                    break
                }
            }
            cursor.close()

            if (!isLoginColumnExists) {
                db.execSQL("ALTER TABLE user ADD COLUMN isLogin INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}