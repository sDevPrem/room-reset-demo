package com.sdevprem.roomresetdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Student::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context : Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, "student_db"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }

    abstract fun getStudentDao() : StudentDao
    abstract fun getDatabaseDao() : DatabaseDao
}