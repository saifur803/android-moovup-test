package com.androidtest.moovup.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.ActivityPeopleDetailBinding
import com.androidtest.moovup.ui.main.ContainerFragment
import com.androidtest.moovup.ui.map.MapFragment

class PeopleDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPeopleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peopleId = intent.getStringExtra(MapFragment.PEOPLE_ID)
        if (peopleId == null) {
            finish()
        }

        binding.backBtn.setOnClickListener { finish() }

        val mapFragment = MapFragment()
        mapFragment.arguments = Bundle().apply {
            putString(MapFragment.PEOPLE_ID, peopleId)
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameDetail, mapFragment, ContainerFragment.TAG)
            .commit()
    }
}