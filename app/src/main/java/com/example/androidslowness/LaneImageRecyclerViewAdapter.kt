package com.example.androidslowness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import android.net.Uri
import java.util.logging.Logger

class LaneImageRecyclerViewAdapter(
    private val imgSources: List<String>,
    private val context: Context
) : RecyclerView.Adapter<LaneImageRecyclerViewHolder>() {
    override fun getItemCount(): Int {
        return imgSources.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LaneImageRecyclerViewHolder {
        return LaneImageRecyclerViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_lane_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LaneImageRecyclerViewHolder, position: Int) {
        Logger.getAnonymousLogger().warning("Setting source to ${imgSources[position]}")

        // Get the screen size to set the image's width equal to it
        val screenSize = getScreenSize(context)

        holder.laneImageView.layoutParams.width = screenSize.width
        holder.laneImageView.setImageURI(imgSources[position])
    }
}

class LaneImageRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val laneImageView: SimpleDraweeView = view.findViewById(R.id.lane_image_view)
}