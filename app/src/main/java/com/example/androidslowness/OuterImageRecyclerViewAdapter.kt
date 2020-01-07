package com.example.androidslowness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OuterImageRecyclerViewAdapter(
    private val imgSources: List<List<String>>,
    private val context: Context
) :
    RecyclerView.Adapter<OuterImageRecyclerViewHolder>() {

    override fun getItemCount(): Int {
        return imgSources.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OuterImageRecyclerViewHolder {
        return OuterImageRecyclerViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_outer_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OuterImageRecyclerViewHolder, position: Int) {
        holder.laneTitle.text = "Lane ${position + 1}"
        holder.innerRecyclerView.adapter =
            LaneImageRecyclerViewAdapter(imgSources[position], context)
        holder.innerRecyclerView.layoutManager =
            CustomLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}

class OuterImageRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val laneTitle: TextView = view.findViewById(R.id.lane_title)
    val innerRecyclerView: RecyclerView = view.findViewById(R.id.lane_recycler_view)
}