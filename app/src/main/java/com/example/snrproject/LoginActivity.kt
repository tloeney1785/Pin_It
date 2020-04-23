package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.api.load
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginActivity : AppCompatActivity() {

    internal var dbUsers = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        back_to_register_textview.setOnClickListener{

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener(){
            val dbUsers = dbUsers.allData
            for (i in dbUsers.indices) {

                //filter by profile
                if((dbUsers[i].userName==login_username_edittext.text.toString() && dbUsers[i].userPass==login_password_edittext.text.toString()) ||
                    (dbUsers[i].userEmail==login_email_edittext.text.toString() && dbUsers[i].userPass==login_password_edittext.text.toString()))
                {val intent = Intent(this, ListPics::class.java)
                    intent.putExtra("username",login_username_edittext.text.toString())
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@LoginActivity, "INCORRECT LOGIN CREDENTIALS", Toast.LENGTH_LONG).show()
                    login_password_edittext.setText("")
                    login_username_edittext.setText("")
                    login_email_edittext.setText("")
                }
            }

        }
    }



}