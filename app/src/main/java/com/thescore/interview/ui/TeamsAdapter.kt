package com.thescore.interview.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thescore.interview.data.Team

class TeamsAdapter(private var items: List<Team>) : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    fun updateItems(newItems: List<Team>) {
        items = newItems
        notifyDataSetChanged()
    }
}

