package com.example.snrproject

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import coil.api.load
import kotlinx.android.synthetic.main.activity_list_pics.*
import android.view.Gravity
import android.content.Intent
import android.widget.LinearLayout


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
            val user = TextView(this)

            // use layoutParams to set image styling
            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.gravity = Gravity.CENTER
            image.setLayoutParams(layoutParams)
            user.setLayoutParams(layoutParams)

            image.load(res[i].userURL)
            user.text = res[i].userName
            layout.addView(image)
            layout.addView(user)
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, ListPics::class.java))
        }

        btnUpload.setOnClickListener {
            startActivity((Intent(this, MainActivity::class.java)))
        }
    }
}