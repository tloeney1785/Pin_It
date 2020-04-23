package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        back_to_register_textview.setOnClickListener{

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener(){
            val intent = Intent(this, ListPics::class.java)
            intent.putExtra("username",login_username_edittext.text.toString())
            startActivity(intent)
        }
    }



}