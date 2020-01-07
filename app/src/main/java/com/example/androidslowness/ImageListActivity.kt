package com.example.androidslowness

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_image_list.*
import java.util.logging.Logger
import kotlin.math.floor
import kotlin.math.roundToInt

class ImageListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        val primarySize = intent.getIntExtra("primarySize", 10)
        val secondarySize = intent.getIntExtra("secondarySize", 10)
        val resolutionRatio = intent.getDoubleExtra("resolutionRatio", 1.0)

        val screenSize = getScreenSize(this)
        val imageWidth = (screenSize.width * resolutionRatio).roundToInt()
        val imageHeight = (imageWidth * 9 / 16.0).roundToInt()

        val randomImagesList = (1..primarySize).map { n ->
            getRandomImages(
                secondarySize,
                n * secondarySize,
                imageWidth,
                imageHeight
            )
        }

        this.imageRecyclerView.layoutManager =
            CustomLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        this.imageRecyclerView.adapter = OuterImageRecyclerViewAdapter(randomImagesList, this)
    }
}
