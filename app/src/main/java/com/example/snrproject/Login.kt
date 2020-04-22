package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        button_register.setOnClickListener{
            val email = email_register.text.toString()
            val password = password_register.text.toString()

            Log.d("Login", "Email is: " + email)
            Log.d("Login", "Password: $password")

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(){
                    if(!it.isSuccessful)
                        return@addOnCompleteListener
                }


        }
        account_exist_textview.setOnClickListener{
            Log.d("Login","Try to show login activity")

            //launch the login activity
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}
