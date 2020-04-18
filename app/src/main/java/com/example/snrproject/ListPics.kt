package com.example.snrproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.transform.CircleCropTransformation

import kotlinx.android.synthetic.main.activity_list_pics.*
import kotlinx.android.synthetic.main.content_list_pics.*

class ListPics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pics)
        setSupportActionBar(toolbar)
        imageView3.load("https://tinyurl.com/ydx52qfx")
        imageView2.load("https://tinyurl.com/ydx52qfx")
        imageView4.load("https://tinyurl.com/ydx52qfx")
        imageView5.load("https://tinyurl.com/ydx52qfx")
    }
}
