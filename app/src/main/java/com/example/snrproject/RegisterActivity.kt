package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import android.util.Log
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    internal var dbUsers = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        button_register.setOnClickListener{
            val email = email_register.text.toString()
            val password = password_register.text.toString()
            val username = username_register.text.toString()
            dbUsers.insertData(username,password,email)

            Log.d("Login", "Email is: " + email)
            Log.d("Login", "Password: $password")


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(){
                    if(!it.isSuccessful)
                        return@addOnCompleteListener
                }

        }

        accountexistText.setOnClickListener{

            //launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        }
    }

