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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
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

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Check if the column already exists
            val cursor = db.query("PRAGMA table_info(jemput_sampah)")
            var columnExists = false
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex("name")
                if (columnIndex >= 0) {
                    val columnName = cursor.getString(columnIndex)
                    if (columnName == "paymentMethod") {
                        columnExists = true
                        break
                    }
                }
            }
            cursor.close()

            // Add the column if it does not exist
            if (!columnExists) {
                db.execSQL("""
                ALTER TABLE jemput_sampah ADD COLUMN paymentMethod TEXT NOT NULL DEFAULT ''
            """.trimIndent())
            }
        }
    }


    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Add the ggPoints column to the old user table if it does not exist
            db.execSQL("ALTER TABLE user ADD COLUMN ggPoints INTEGER NOT NULL DEFAULT 0")

            // Create a new table with the correct column order
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS user_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                email TEXT NOT NULL,
                password TEXT NOT NULL,
                isLogin INTEGER NOT NULL,
                ggPoints INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())

            // Copy the data from the old table to the new table
            db.execSQL("""
            INSERT INTO user_new (id, email, password, isLogin, ggPoints)
            SELECT id, email, password, CASE WHEN isLogin THEN 1 ELSE 0 END, ggPoints FROM user
        """.trimIndent())

            // Remove the old table
            db.execSQL("DROP TABLE user")

            // Rename the new table to the old table name
            db.execSQL("ALTER TABLE user_new RENAME TO user")
        }
    }
}