package com.example.androidslowness

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.core.view.marginLeft
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var primarySize: Int = 10
    private var secondarySize: Int = 10
    private var resolutionRatio: Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Fresco with more Logcat output
        val requestListeners = HashSet<RequestListener>()
        requestListeners.add(RequestLoggingListener())
        val imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(this, imagePipelineConfig)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.buttonOpenList.setOnClickListener { _ ->
            val intent = Intent(this, ImageListActivity::class.java).apply {
                putExtra("primarySize", primarySize)
                putExtra("secondarySize", secondarySize)
                putExtra("resolutionRatio", resolutionRatio)
            }
            startActivity(intent)
        }

        this.seekBarPrimarySize.setOnSeekBarChangeListener(SeekBarChangeListener { n ->
            primarySize = n
            this.textViewPrimarySize.text = "Primary Size: $n"
        })
        this.seekBarSecondarySize.setOnSeekBarChangeListener(SeekBarChangeListener { n ->
            secondarySize = n
            this.textViewSecondarySize.text = "Secondary Size: $n"
        })
        val resolutions = arrayListOf<ResolutionRatio>()
        resolutions.add(ResolutionRatio(0, 1 / 3.0, "One third size images (1/9 area)"))
        resolutions.add(ResolutionRatio(1, 0.5, "Half size images (1/4 area)"))
        resolutions.add(ResolutionRatio(2, 0.75, "Three-quarter size images (around 56% area)"))
        resolutions.add(ResolutionRatio(3, 1.5, "Full size images"))
        resolutions.add(ResolutionRatio(4, 1.5, "50% larger (2.25x area)"))
        resolutions.add(ResolutionRatio(5, 2.0, "100% larger (4x area)"))
        this.resolution_ratio_spinner.adapter =
            ResolutionRatioSpinnerAdapter(this, R.id.resolution_ratio_spinner, resolutions)
        this.resolution_ratio_spinner.setSelection(3)
        this.resolution_ratio_spinner.onItemSelectedListener =
            ResolutionRatioChangedListener { d -> resolutionRatio = d }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class SeekBarChangeListener(private val onChanged: (Int) -> Unit) :
    SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChanged(progress)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        return
    }
}

data class ResolutionRatio(val id: Int, val ratio: Double, val name: String)

class ResolutionRatioSpinnerAdapter(
    context: Context,
    resource: Int,
    items: ArrayList<ResolutionRatio>
) : ArrayAdapter<ResolutionRatio>(
    context, resource, items
) {
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id?.toLong() ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return TextView(context).also {
            it.text = getItem(position)?.name
            it.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60
            ).also { layoutParams ->
                layoutParams.bottomMargin = 5
                layoutParams.topMargin = 5
            }
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: getView(position, convertView, parent)
    }
}

class ResolutionRatioChangedListener(private val onChanged: (Double) -> Unit) :
    AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        onChanged((parent?.selectedItem as ResolutionRatio).ratio)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onChanged(1.0)
    }
}