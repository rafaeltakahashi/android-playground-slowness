package com.example.androidslowness

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

data class ScreenSize(val width: Int, val height: Int)

fun getScreenSize(context: Context): ScreenSize {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    return ScreenSize(displayMetrics.widthPixels, displayMetrics.heightPixels)
}