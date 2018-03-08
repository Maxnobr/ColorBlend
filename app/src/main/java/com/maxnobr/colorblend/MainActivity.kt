@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.maxnobr.colorblend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var leftColor = Color.RED
    private var rightColor = Color.BLUE

    private var leftChoose = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                blendColors()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val rnd = Random()
        leftColor = Color.rgb(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255))
        rightColor = Color.rgb(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255))
        textView.setBackgroundColor(leftColor)
        textView3.setBackgroundColor(rightColor)
        blendColors()
        updateButton(true)
    }

    fun leftClick(view:View)
    {
        updateButton(true)
    }

    fun rightClick(view:View)
    {
        updateButton(false)
    }

    private fun updateButton(isLeft : Boolean) {
        leftChoose = isLeft
        if(isLeft)
            button.text = "<--Choose Left  Color"
        else
            button.text = "   Choose Right Color-->"
    }

    fun getColor(view:View)
    {
        Log.i("SashaLog","Sending intent!")
        startActivityForResult(Intent("msud.cs3013.ACTION_COLOR"),1)
    }

    private fun blendColors()
    {
        //textView2.setBackgroundColor((leftColor*(seekBar2.max-seekBar2.progress)+rightColor*seekBar2.progress)/(2*seekBar2.max))
        textView2.setBackgroundColor(
                Color.rgb((Color.red(leftColor)*(seekBar2.max-seekBar2.progress)+Color.red(rightColor)*seekBar2.progress)/seekBar2.max,
                        (Color.green(leftColor)*(seekBar2.max-seekBar2.progress)+Color.green(rightColor)*seekBar2.progress)/seekBar2.max,
                        (Color.blue(leftColor)*(seekBar2.max-seekBar2.progress)+Color.blue(rightColor)*seekBar2.progress)/seekBar2.max))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 1 && resultCode == RESULT_OK) {
            if (leftChoose) {
                leftColor = Integer.parseInt(data?.data.toString())
                textView.setBackgroundColor(leftColor)
                updateButton(false)
            } else {
                rightColor = Integer.parseInt(data?.data.toString())
                textView3.setBackgroundColor(rightColor)
                updateButton(true)
            }
            blendColors()
        }
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
