package com.androidtest.moovup.util

import android.app.AlertDialog
import android.content.Context

class UIUtil {
    companion object {
        fun showSimpleDialog(context: Context,
                             title: String,
                             message: String,
                             onPositive: (() -> Unit)? = null,
                             onNegative: (() -> Unit)? = null) {
            val builder = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Retry") { p0, p1 ->
                    onPositive?.invoke()
                }
                .setNegativeButton("Cancel") { p0, p1 ->
                    onNegative?.invoke()
                }
                .setOnCancelListener {
                    onNegative?.invoke()
                }
            builder.create().show()
        }
    }
}