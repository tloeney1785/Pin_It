package com.example.snrproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    /*
     * CREATE
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,user TEXT,pass TEXT,location TEXT, url TEXT)")
    }

    /*
     * UPDATE
     */
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    /*
     * INSERT
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

    /*
     * DELETE
     */
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    /*
    * Returns a List<> of all Users
    */
    val allData : List<Users>
        get() {
            val db = this.writableDatabase
            val list = ArrayList<Users>()
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
            if (cursor != null) {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val userID = cursor.getString(cursor.getColumnIndex("id"))
                        val userName = cursor.getString(cursor.getColumnIndex("user"))
                        val userPass = cursor.getString(cursor.getColumnIndex("pass"))
                        val userLocation = cursor.getString(cursor.getColumnIndex("location"))
                        val userURL = cursor.getString(cursor.getColumnIndex("url"))
                        val user = Users(userID, userName, userPass, userLocation, userURL)
                        list.add(user)
                    } while (cursor.moveToNext())
                }
            }
            return list
        }
    /*
    * Companion object to hold static fields
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
}