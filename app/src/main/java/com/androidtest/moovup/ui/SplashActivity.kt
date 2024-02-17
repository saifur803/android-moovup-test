package com.androidtest.moovup.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.androidtest.moovup.api.ApiClient
import com.androidtest.moovup.databinding.ActivitySplashBinding
import com.androidtest.moovup.ui.main.MainActivity
import com.androidtest.moovup.util.UIUtil

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeApi()
    }

    private fun initializeApi() {
        binding.splashProgressBar.visibility = View.VISIBLE

        val apiClient = ApiClient.getInstance(this)

        apiClient.fetchPeopleList { result, isDataAvailable ->
            binding.splashProgressBar.visibility = View.GONE

            if (!result && !isDataAvailable) {
                showErrorDialog()
                return@fetchPeopleList
            }

            openMainActivity()
        }
    }

    private fun openMainActivity() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

    private fun showErrorDialog() {
        UIUtil.showSimpleDialog(
            this,
            "Failed",
            "Failed to download data,\nplease try again later",
            onNegative = {
                finish()
            },
            onPositive = {
                initializeApi()
            }
        )
    }
}