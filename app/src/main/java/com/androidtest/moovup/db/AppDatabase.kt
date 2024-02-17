package com.androidtest.moovup.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidtest.moovup.db.dao.PeopleDao
import com.androidtest.moovup.db.model.People
import com.androidtest.moovup.util.CoroutineUtil

@Database(
    entities = [
        People::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        var instance: AppDatabase? = null

        fun createDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "moovup_test_app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun peopleDao(): PeopleDao

    fun getPeopleList(callback: ((List<People>) -> Unit)) {
        CoroutineUtil.io {
            val peopleList = peopleDao().getPeopleList()
            CoroutineUtil.main {
                callback(peopleList)
            }
        }
    }
}