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
        imageView3.load("https://www.nerd-age.com/wp-content/uploads/2012/12/Jojos-Bizarre-Adventure-Joseph-in-Drag.jpg")
        {
            crossfade(enable = true)
            crossfade(3000)
        }
        imageView2.load("https://images.unsplash.com/photo-1558981806-ec527fa84c39?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=600&q=60")
        {
            crossfade(enable = true)
            crossfade(3000)
        }
        imageView4.load("https://images.unsplash.com/photo-1582648440777-95268093ebf7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=600&q=60")
        {
            crossfade(enable = true)
            crossfade(3000)
        }
        imageView5.load("https://www.nerd-age.com/wp-content/uploads/2012/12/Jojos-Bizarre-Adventure-Joseph-in-Drag.jpg")
        {
            crossfade(enable = true)
            crossfade(3000)
        }
    }

}
