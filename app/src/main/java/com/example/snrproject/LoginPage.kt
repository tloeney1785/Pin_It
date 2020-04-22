package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        back_to_register_textview.setOnClickListener{

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }



}
