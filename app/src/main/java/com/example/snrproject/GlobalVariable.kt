package com.example.snrproject

import android.app.Application

class GlobalVariable : Application (){
    var globalUser:String = "default"
        get() = field
        set(value) {field = value}
}