package com.example.snrproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_page.*
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    internal var dbUsers = UserDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        back_to_register_textview.setOnClickListener{

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val dbUsers = dbUsers.allData

        fun hash(pass:String): String {
            val bytes = pass.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }

        loginBtn.setOnClickListener(){
            for (i in dbUsers.indices) {

                //filter by profile
                if((dbUsers[i].userName==login_username_edittext.text.toString() && dbUsers[i].userPass==hash(login_password_edittext.text.toString())) ||
                    (dbUsers[i].userEmail==login_email_edittext.text.toString() && dbUsers[i].userPass==hash(login_password_edittext.text.toString())))
                {
                    val intent = Intent(this, ListPics::class.java)
                    intent.putExtra("username",login_username_edittext.text.toString())
                    startActivity(intent)
                    break
                }
                else if (i == dbUsers.size - 1)
                    Toast.makeText(this@LoginActivity, "INCORRECT LOGIN CREDENTIALS", Toast.LENGTH_LONG).show()
            }
        }
    }



}