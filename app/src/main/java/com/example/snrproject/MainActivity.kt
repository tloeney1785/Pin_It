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
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import kotlinx.android.synthetic.main.content_list_pics.*
import okhttp3.HttpUrl
import java.io.File
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class MainActivity : AppCompatActivity() {
    // In Kotlin `var` is used to declare a mutable variable. On the other hand
    // `internal` means a variable is visible within a given module.
    internal var dbHelper = DatabaseHelper(this)

    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /*
     * Alert dialog with data dialog
     */
    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /*
     * Clear editable text
     */
    fun clearEditTexts(){
        userTxt.setText("")
        passTxt.setText("")
        idTxt.setText("")
        locationTxt.setText("")
        urlTxt.setText("")
    }

    /*
     * onCreate method.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val res = dbHelper.allData
        imageView1.load(res[0].userURL) // shows the first entry's img

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
        handleNext()
    }

    /*
     * NEXT PAGE button clicked
     */
    fun handleNext() {
        NextBtn.setOnClickListener{
            val intent = Intent (this,ListPics::class.java)
            startActivity(intent)
        }
    }

    /*
     * ENTER button clicked
     */
    fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(userTxt.text.toString(),passTxt.text.toString(),
                    locationTxt.text.toString(),urlTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }

            val res = dbHelper.allData

            val imageView = ImageView(this)
            val imgResId = R.drawable.ic_launcher_background

            var resId = imgResId
            imageView.setImageResource(imgResId)

            val linearLayout = findViewById<LinearLayout>(R.id.rootContainer)
            // Add ImageView to LinearLayout
            imageView.load(res[0].userURL)
            linearLayout?.addView(imageView)
        }
    }

    /*
     * UPDATE button clicked
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
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /*
     * DELETE button clicked
     */
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

    /*
     * VIEW button clicked
     */
    fun handleViewing() {
        viewBtn.setOnClickListener(
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.isEmpty()) {
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                for (i in res.indices) {
                    buffer.append("ID :" + res[i].userID + "\n")
                    buffer.append("USER :" + res[i].userName + "\n")
                    buffer.append("PASS :" + res[i].userPass + "\n")
                    buffer.append("LOCATION :" + res[i].userLocation + "\n")
                    buffer.append("URL :" + res[i].userURL + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
            }
        )
    }
}
