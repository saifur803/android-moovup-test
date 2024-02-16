package com.androidtest.moovup.util

import android.content.Context
import androidx.room.RoomDatabase

open class SingletonHolder<out I, in C>(val constructor: (C) -> I) {
    @Volatile
    private var instance: I? = null

    fun getInstance(args: C): I {
        return instance?: constructor(args).also {
            instance = it
        }
    }

    fun removeInstance() {
        instance = null
    }
}