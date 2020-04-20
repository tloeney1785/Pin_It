package com.example.snrproject

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import coil.api.load
import kotlinx.android.synthetic.main.activity_list_pics.*
import kotlinx.android.synthetic.main.content_list_pics.*

class ListPics : AppCompatActivity() {
    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pics)
        setSupportActionBar(toolbar)

        /*
         * Load all images in list view
         */
        val layout: LinearLayout = findViewById(R.id.rootContainer)
        val res = dbHelper.allData
        for (i in res.indices) {
            val image = ImageView(this)
            image.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            image.load(res[i].userURL)
            layout.addView(image)
        }
    }
}