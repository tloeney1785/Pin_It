package com.example.snrproject

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_list_pics.btnHome
import kotlinx.android.synthetic.main.activity_list_pics.btnProfile
import kotlinx.android.synthetic.main.activity_list_pics.btnUpload
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*


class ProfileActivity : AppCompatActivity(){

    private var dbUsers = DatabaseHelper(this)
    private var dbImages = ImageDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        loadProfilePicture(getUsername())

        profilenameTxt.text = getUsername()
        setupButtons(getUsername())
        listPhotos(getUsername())

        profilePictureBtn.setOnClickListener(){
            Log.d("ProfileActivity", "Profile picture select")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected was
            Log.d("ProfileActivity", "Photo was selected")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            selectphoto_imageview_register.setImageBitmap(bitmap)
            profilePictureBtn.alpha = 0f

            uploadImageToFirebaseStorage()
        }
    }

    private fun loadProfilePicture(username: String){
        val User = dbUsers.allData
        for(i in User.indices){
            if(User[i].userName==username){
                Log.d("ProfileActivity", "Username was found, loading profile picture")
                selectphoto_imageview_register.load(User[i].userURL)
                profilePictureBtn.alpha = 0f

                //val layout: LinearLayout = findViewById(R.id.rootContainer)
                //layout.addView(image)
                break
            }
        }
    }

    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri==null)return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("ProfileActivity", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("ProfileActivity", "File Location: $it")
                    dbUsers.updateURL(user = getUsername().toString(), url = it.toString())
                }
            }
    }


    private fun getUsername():String{
        val username = intent.getStringExtra("username")
        return username
    }

    private fun listPhotos(username:String){
        /*
        * Load all images in list view
        */
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val layout: LinearLayout = findViewById(R.id.rootContainer)
        val dbImages = dbImages.allData

        for (i in dbImages.indices) {
            val user = TextView(this)
            val loc = TextView(this)
            val image = ImageView(this)
            val caption = TextView(this)

            // use layouts to set styling
            val usrLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val locLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val imgLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val captionLayout = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            usrLayout.gravity = Gravity.LEFT
            locLayout.gravity = Gravity.LEFT
            imgLayout.gravity = Gravity.CENTER
            captionLayout.gravity = Gravity.LEFT

            user.setLayoutParams(usrLayout)
            loc.setLayoutParams(locLayout)
            image.setLayoutParams(imgLayout)
            caption.setLayoutParams(captionLayout)

            user.text = dbImages[i].userName
            loc.text = dbImages[i].userLocation
            image.load(dbImages[i].userURL)
            caption.text = dbImages[i].userCaption

            // filter by profile
            if(dbImages[i].userName == username) {
                layout.addView(user)
                layout.addView(loc)
                layout.addView(image)
                layout.addView(caption)
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

}