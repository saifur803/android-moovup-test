package com.androidtest.moovup.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    companion object {
        const val TAG = "MapFragment"
    }

    lateinit var binding: FragmentMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)

        return binding.root
    }
}