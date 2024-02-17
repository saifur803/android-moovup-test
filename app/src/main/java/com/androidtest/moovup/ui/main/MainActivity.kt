package com.androidtest.moovup.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val containerFragment = ContainerFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameMain, containerFragment, ContainerFragment.TAG)
            .commit()
    }
}