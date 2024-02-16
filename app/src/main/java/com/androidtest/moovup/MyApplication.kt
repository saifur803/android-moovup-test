package com.androidtest.moovup

import android.app.Application
import com.androidtest.moovup.db.AppDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.createDatabase(this)
    }
}