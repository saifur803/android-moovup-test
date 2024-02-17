package com.androidtest.moovup.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidtest.moovup.databinding.FragmentListBinding

class ListFragment: Fragment() {
    companion object {
        const val TAG = "ListFragment"
    }

    lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = PeopleListAdapter(requireContext())
        binding.recyclerViewPeople.setHasFixedSize(true)
        binding.recyclerViewPeople.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPeople.adapter = adapter
    }
}