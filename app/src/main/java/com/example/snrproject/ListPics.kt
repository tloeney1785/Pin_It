package com.example.snrproject

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import coil.api.load
import kotlinx.android.synthetic.main.activity_list_pics.*

class ListPics : AppCompatActivity() {
    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pics)

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