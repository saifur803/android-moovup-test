package com.androidtest.moovup.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CoroutineUtil {
    companion object {
        fun io(job: suspend (() -> Unit)): Job {
            return CoroutineScope(Dispatchers.IO).launch {
                job.invoke()
            }
        }

        fun main(job: suspend (() -> Unit)): Job {
            return CoroutineScope(Dispatchers.Main).launch {
                job.invoke()
            }
        }
    }
}