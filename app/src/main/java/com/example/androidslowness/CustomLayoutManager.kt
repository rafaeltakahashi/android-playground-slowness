package com.example.androidslowness

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

// Subclass of LinearLayoutManager that loads extra content off the screen
class CustomLayoutManager(private val context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {
    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return when (orientation) {
            HORIZONTAL -> getScreenSize(context).width * 2
            VERTICAL -> (getScreenSize(context).height * 1.5).roundToInt()
            else -> 0
        }
    }
}

