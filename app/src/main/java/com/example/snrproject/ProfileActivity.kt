package com.example.snrproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams.*
import android.widget.*
import coil.api.load
import android.view.Gravity
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_list_pics.btnHome
import kotlinx.android.synthetic.main.activity_list_pics.btnProfile
import kotlinx.android.synthetic.main.activity_list_pics.btnUpload
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(){

    private var dbHelper = DatabaseHelper(this)
    private var dbImages = ImageDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val username = intent.getStringExtra("username")
        profilenameTxt.text = username
        setupButtons(username)
        listPhotos(username)

        profilePictureBtn.setOnClickListener(){
            Log.d("profileactivity", "Profile picture select")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check waht the selected was
            Log.d("ProfileActivity", "Photo was selected")

            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            selectphoto_imageview_register.setImageBitmap(bitmap)
            profilePictureBtn.alpha = 0f
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //profilePictureBtn.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun listPhotos(username:String){
        /*
        * Load all images in list view
        */

        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val layout: LinearLayout = findViewById(R.id.rootContainer)
        val dbImages = dbImages.allData

        for (i in dbImages.indices) {
            val image = ImageView(this)
            val user = TextView(this)

            // use layoutParams to set image styling

            layoutParams.gravity = Gravity.CENTER_HORIZONTAL

            image.setLayoutParams(layoutParams)
            user.setLayoutParams(layoutParams)

            //filter by profile
            if(dbImages[i].userName==username){
                //load image and username
                image.load(dbImages[i].userURL)
                user.text = username
                layout.addView(image)
                layout.addView(user)
            }
        }
    }

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
    /*
     * DELETE button clicked
     */
    /*private fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }*/
}