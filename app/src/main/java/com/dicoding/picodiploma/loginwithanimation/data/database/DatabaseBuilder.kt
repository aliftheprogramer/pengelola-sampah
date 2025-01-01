package com.dicoding.picodiploma.loginwithanimation.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {
    private var INSTANCE: AppRoomDatabase? = null

    fun getInstance(context: Context): AppRoomDatabase {
        if (INSTANCE == null) {
            synchronized(AppRoomDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java, "app_database"
                ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
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
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS notification (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    title TEXT NOT NULL,
                    body TEXT NOT NULL,
                    dateTime TEXT NOT NULL
                )
            """.trimIndent())
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS notification (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    title TEXT NOT NULL,
                    body TEXT NOT NULL,
                    dateTime TEXT NOT NULL
                )
            """.trimIndent())
        }
    }
}