package com.example.snrproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    //var database: SQLiteDatabase

    /*
     * Our onCreate() method.
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the tables
     * should happen.
     */

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,user TEXT,pass TEXT,location TEXT, url TEXT)")
    }

    /*
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertData(user: String, pass: String, location: String, url: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER, user)
        contentValues.put(COLUMN_PASS, pass)
        contentValues.put(COLUMN_LOCATION, location)
        contentValues.put(COLUMN_URL, url)
        db.insert(TABLE_NAME, null, contentValues)
    }
    //Let's create  a method to update a row with new field values.
    fun updateData(id: String, user: String, pass: String, location: String, url: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id)
        contentValues.put(COLUMN_USER, user)
        contentValues.put(COLUMN_PASS, pass)
        contentValues.put(COLUMN_LOCATION, location)
        contentValues.put(COLUMN_URL, url)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

     //Let's create a function to delete a given row based on the id.

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    //The below getter property will return a Cursor containing our dataset.
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
            return res
        }
    /*
    * Let's create a companion object to hold our static fields.
    * A Companion object is an object that is common to all instances of a given
    * class.*/

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "login.db"
        private const val TABLE_NAME = "userdata"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER = "user"
        private const val COLUMN_PASS = "pass"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_URL = "url"
    }

   /* init {
        database = writableDatabase
    }*/
}