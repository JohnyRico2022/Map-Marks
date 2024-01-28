package ru.nikita.mapmarks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nikita.mapmarks.dao.MarksDao
import ru.nikita.mapmarks.entity.MarksEntity

@Database(entities = [MarksEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun marksDao(): MarksDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .allowMainThreadQueries()
                .build()
    }
}