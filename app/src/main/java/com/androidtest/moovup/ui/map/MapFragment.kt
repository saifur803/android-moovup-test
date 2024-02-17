package com.androidtest.moovup.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.FragmentMapBinding
import com.androidtest.moovup.db.AppDatabase
import com.androidtest.moovup.db.model.People
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    companion object {
        const val TAG = "MapFragment"

        const val PEOPLE_ID = "map-selected-people-id"
    }

    lateinit var binding: FragmentMapBinding
    lateinit var mapFragment: SupportMapFragment

    private var selectedPeopleId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        binding.mapPeopleDetailView.visibility = View.GONE

        arguments?.let {
            selectedPeopleId = it.getString(PEOPLE_ID)
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync { doWhenMapIsReady(it) }

        return binding.root
    }

    private fun doWhenMapIsReady(map: GoogleMap) {
        val db = AppDatabase.createDatabase(requireContext())

        if (selectedPeopleId != null) {
            db.getPeopleById(selectedPeopleId!!) { people ->
                showDetailsView(people)
                val isAdded = map.addPeopleMarker(people, 15F,true)

                if (!isAdded) {
                    Toast.makeText(
                        requireContext(), "Location Unavailable", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            return
        }

        db.getPeopleList { peopleList ->
            peopleList.forEach { people ->
                map.addPeopleMarker(people, 9F)
            }
        }
    }

    private fun showDetailsView(people: People) {
        binding.mapPeopleDetailView.visibility = View.VISIBLE

        Glide.with(requireContext())
            .load(people.picture)
            .into(binding.mapPeopleImage)

        binding.mapPeopleNameText.text = String.format("%s %s", people.firstName, people.lastName)
        binding.mapPeopleEmailText.text = people.email
    }

    private fun GoogleMap.addPeopleMarker(people: People,
                                  zoom: Float,
                                  clearPrevious: Boolean = false): Boolean {
        val latLng = people.getLocation()?: return false

        val markerOpts = MarkerOptions().apply {
            position(latLng)
            title("${people.firstName} ${people.lastName}")
        }
        if (clearPrevious) {
            clear()
        }
        animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        addMarker(markerOpts)

        return true
    }
}