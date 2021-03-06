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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import kotlinx.android.synthetic.main.content_profile.*

class ListPics : AppCompatActivity() {
    private var dbImages = ImageDatabase(this)
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username")
        setContentView(R.layout.activity_list_pics)
        setupButtons(username)
        loadMap()
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

    // load map
    private fun loadMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            googleMap.isMyLocationEnabled = true
        })
    }

    //load images from database
    private fun loadImages(){
        /*
         * Load all images in list view
         */
        val layout: LinearLayout = findViewById(R.id.rootContainer)
        val res = dbImages.allData
        for (i in res.size-1 downTo 0) {
            val user = TextView(this)
            val loc = TextView(this)
            val image = ImageView(this)
            val caption = TextView(this)

            // use layouts to set styling
            val usrLayout = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            val locLayout = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            val imgLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val captionLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            usrLayout.gravity = Gravity.LEFT
            locLayout.gravity = Gravity.LEFT
            imgLayout.gravity = Gravity.CENTER
            imgLayout.width = 1100
            imgLayout.height = 1100
            captionLayout.gravity = Gravity.LEFT

            image.setLayoutParams(imgLayout)
            user.setLayoutParams(usrLayout)
            loc.setLayoutParams(locLayout)
            caption.setLayoutParams(captionLayout)


            user.text = res[i].userName
            loc.text = res[i].userLocation
            image.load(res[i].userURL)
            caption.text = res[i].userCaption

            layout.addView(image)
            layout.addView(loc)
            layout.addView(user)
            layout.addView(caption)

            // get pins
            var coordinates = res[i].userLocation.split(", ")
            //var location = LatLng(coordinates[0].toDouble(), coordinates[1].toDouble()) // create coordinate
//            googleMap.addMarker(MarkerOptions().position(location)) // add pin to location
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f)) // zoom map to general area
        }
    }
}

