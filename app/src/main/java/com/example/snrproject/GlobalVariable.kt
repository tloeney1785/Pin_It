package com.example.snrproject

import android.app.Application

class GlobalVariable : Application (){
    var globalUser = "test"

    fun setUser(username:String){
        globalUser = username
    }

    fun getUser(): String {
        return globalUser
    }

}