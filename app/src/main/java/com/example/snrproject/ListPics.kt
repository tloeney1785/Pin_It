package com.example.snrproject

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import coil.api.load
import kotlinx.android.synthetic.main.activity_list_pics.*
import android.view.Gravity
import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login_page.*


class ListPics : AppCompatActivity() {
    private var dbImages = ImageDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username")
        setContentView(R.layout.activity_list_pics)
        setupButtons(username)
        loadImages()
    }

    //setup Home, Profile and Upload buttons
     private fun setupButtons(username:String){
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, ListPics::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }

        btnUpload.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }

    }
    //load images from database
    private fun loadImages(){
        /*
         * Load all images in list view
         */
        val layout: LinearLayout = findViewById(R.id.rootContainer)
        val res = dbImages.allData
        for (i in res.indices) {
            val image = ImageView(this)
            val user = TextView(this)
            val loc = TextView(this)

            // use layoutParams to set image styling
            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.gravity = Gravity.CENTER
            image.setLayoutParams(layoutParams)
            user.setLayoutParams(layoutParams)
            loc.setLayoutParams(layoutParams)


            image.load(res[i].userURL)
            user.text = res[i].userName
            loc.text = res[i].userLocation
            layout.addView(image)
            layout.addView(user)
            layout.addView(loc)
        }
    }
}

