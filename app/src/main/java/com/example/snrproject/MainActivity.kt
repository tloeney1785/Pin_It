package com.example.snrproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import kotlinx.android.synthetic.main.content_main.*
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler


class MainActivity : AppCompatActivity() {
    // In Kotlin `var` is used to declare a mutable variable. On the other hand
    // `internal` means a variable is visible within a given module.
    //internal var dbHelper = DatabaseHelper(this)
    internal var dbImages = ImageDatabase(this)

    /*
     * onCreate method.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // constantly update image preview by checking urlTxt
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                imagePreview.load(urlTxt.text.toString())
                handler.postDelayed(this, 500) // interval time for refresh
            }
        })

        handleInserts()
        //handleUpdates()
        //handleDeletes()
       // handleViewing()
        handleHome()
        LogoutBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /*
     * Alert dialog with data dialog
     */
    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /*
     * Clear editable text
     */
    private fun clearEditTexts(){
        userTxt.setText("")
        passTxt.setText("")
        idTxt.setText("")
        locationTxt.setText("")
        urlTxt.setText("")
    }

    /*
     * HOME PAGE button clicked
     */
    private fun handleHome() {
        HomeBtn.setOnClickListener{
            startActivity(Intent(this, ListPics::class.java))
        }
    }

    /*
     * ENTER button clicked
     */
    private fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbImages.insertData(userTxt.text.toString(),locationTxt.text.toString(), urlTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /*
     * UPDATE button clicked
     */
   /* private fun handleUpdates() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(
                    idTxt.text.toString(),
                    userTxt.text.toString(),
                    passTxt.text.toString(),
                    locationTxt.text.toString(),
                    urlTxt.text.toString())
                if (isUpdate)
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
    private fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /*
     * VIEW button clicked
     */
    private fun handleViewing() {
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
    }*/
}

