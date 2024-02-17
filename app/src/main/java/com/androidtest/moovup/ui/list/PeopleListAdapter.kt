package com.androidtest.moovup.ui.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.moovup.R
import com.androidtest.moovup.databinding.ViewholderPeopleBinding
import com.androidtest.moovup.db.AppDatabase
import com.androidtest.moovup.db.model.People
import com.androidtest.moovup.ui.map.MapFragment
import com.androidtest.moovup.ui.map.PeopleDetailActivity

class PeopleListAdapter(val context: Context):
    RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {

    private val peopleList: MutableList<People> = ArrayList()

    init {
        val db = AppDatabase.createDatabase(context)
        db.getPeopleList {
            peopleList.addAll(it)
            notifyItemChanged(0, it.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.viewholder_people, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val people = peopleList[position]

        holder.binding.peopleTitleTextView.text = String.format(
            "%s %s", people.firstName, people.lastName
        )

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PeopleDetailActivity::class.java)
            intent.putExtra(MapFragment.PEOPLE_ID, people.id)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ViewholderPeopleBinding.bind(itemView)
    }
}