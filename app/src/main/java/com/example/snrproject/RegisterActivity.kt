package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import android.util.Log
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_page.*
import java.security.MessageDigest


class RegisterActivity : AppCompatActivity() {

    internal var dbUsers = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        button_register.setOnClickListener{
            PerformReigster()
        }


        accountexistText.setOnClickListener{
            //launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        }

        fun hash(pass:String): String {
            val bytes = pass.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }


    private fun saveUserToFireDatabase(){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username_register.toString())

        ref.setValue(user)
            .addOnSuccessListener{
                Log.d("RegisterActivity", "Finally we saved the user to Firebase")
            }
    }

    private fun PerformReigster(){
        val email = email_register.text.toString()
        val password = hash(password_register.text.toString())
        val username = username_register.text.toString()

        //Return if fields are left blank
        if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please fill out all forms", Toast.LENGTH_SHORT).show()
            return
        }
        //Else, insert data into database

        dbUsers.insertData(username,password,email,"")
        Log.d("Login", "Email is: " + email)
        Log.d("Login", "Password: $password")


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                if(!it.isSuccessful)
                    return@addOnCompleteListener
            }

        saveUserToFireDatabase()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

class User(val uid: String, val username: String)
