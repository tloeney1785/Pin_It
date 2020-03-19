package com.example.snrproject


import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import coil.api.load
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoil
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import okhttp3.HttpUrl
import java.io.File

class MainActivity : AppCompatActivity() {

    //In Kotlin `var` is used to declare a mutable variable. On the other hand
    //`internal` means a variable is visible within a given module.
    internal var dbHelper = DatabaseHelper(this)

    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * Let's create a function to show an alert dialog with data dialog.
     */
    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /**
     * Let's create a method to clear our edittexts
     */
    fun clearEditTexts(){
        userTxt.setText("")
        passTxt.setText("")
        idTxt.setText("")
        locationTxt.setText("")
        urlTxt.setText("")
    }

    /**
     * Let's override our onCreate method.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView1.load("https://www.nerd-age.com/wp-content/uploads/2012/12/Jojos-Bizarre-Adventure-Joseph-in-Drag.jpg")
        {
            crossfade(enable = true)
            crossfade(3000)
            transformations(CircleCropTransformation())
        }
        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
        handleNext()
    }
    fun handleNext() {
        NextBtn.setOnClickListener{
            val intent = Intent (this,ListPics::class.java)
            startActivity(intent)
        }
    }
    /**
     * When our handleInserts button is clicked.
     */
    fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(userTxt.text.toString(),passTxt.text.toString(),
                    locationTxt.text.toString(),urlTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }

        }
    }

    /**
     * When our handleUpdates data button is clicked
     */
    fun handleUpdates() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(idTxt.text.toString(),
                    userTxt.text.toString(),
                    passTxt.text.toString(), locationTxt.text.toString(),urlTxt.text.toString())
                if (isUpdate == true)
                    showToast("Data Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    //When our handleDeletes button is clicked

    fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * When our View All is clicked
     */
    fun handleViewing() {
        viewBtn.setOnClickListener(
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.count == 0) {
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n")
                    buffer.append("USER :" + res.getString(1) + "\n")
                    buffer.append("PASS :" + res.getString(2) + "\n")
                    buffer.append("LOCATION :" + res.getString(3) + "\n")
                    buffer.append("URL :" + res.getString(4) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())

            }
        )
    }


}
