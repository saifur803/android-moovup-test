package com.androidtest.moovup.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.FragmentContainerBinding
import com.androidtest.moovup.ui.list.ListFragment
import com.androidtest.moovup.ui.map.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ContainerFragment: BaseContainerFragment() {
    companion object {
        const val TAG = "ContainerFragment"
    }

    lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(layoutInflater)

        binding.containerBottomNav.setOnItemSelectedListener(bottomNavListener)

        setListViewFragment()

        return binding.root
    }

    fun setListViewFragment() {
        if (isFragmentActive(ListFragment.TAG)) {
            return
        }

        if (!showFragment(ListFragment.TAG)) {
            addFragment(ListFragment(), ListFragment.TAG)
        }
    }

    fun setMapViewFragment() {
        if (isFragmentActive(MapFragment.TAG)) {
            return
        }

        if (!showFragment(MapFragment.TAG)) {
            addFragment(MapFragment(), MapFragment.TAG)
        }
    }

    private val bottomNavListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navListView -> {
                    setListViewFragment()
                    true
                }

                R.id.navMapView -> {
                    setMapViewFragment()
                    true
                } else -> {
                false
            }
            }
        }

}