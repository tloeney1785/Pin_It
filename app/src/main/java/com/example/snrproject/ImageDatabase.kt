package com.example.snrproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ImageDatabase(context: Context?) :
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
        db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY " + "AUTOINCREMENT,user TEXT, location TEXT, url TEXT, caption TEXT)")
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
    fun insertData(user: String, location: String, url: String, caption: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER, user)
        contentValues.put(COLUMN_LOCATION, location)
        contentValues.put(COLUMN_URL, url)
        contentValues.put(COLUMN_CAPTION, caption)
        db.insert(TABLE_NAME, null, contentValues)
    }
    //Let's create  a method to update a row with new field values.
    fun updateData(user: String, id: String, location: String, url: String, caption: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id)
        contentValues.put(COLUMN_USER, user)
        contentValues.put(COLUMN_LOCATION, location)
        contentValues.put(COLUMN_URL, url)
        contentValues.put(COLUMN_CAPTION, caption)
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
    val allData : List<Images>
        get() {
            val db = this.writableDatabase
            val list = ArrayList<Images>()
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
            if (cursor != null) {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val userID = cursor.getString(cursor.getColumnIndex("id"))
                        val userName = cursor.getString(cursor.getColumnIndex("user"))
                        val userLocation = cursor.getString(cursor.getColumnIndex("location"))
                        val userURL = cursor.getString(cursor.getColumnIndex("url"))
                        val userCaption = cursor.getString(cursor.getColumnIndex("caption"))
                        val user = Images(userID, userName, userLocation, userURL, userCaption)
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
        private const val DATABASE_NAME = "images.db"
        private const val TABLE_NAME = "imgdata"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER = "user"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_URL = "url"
        private const val COLUMN_CAPTION = "caption"
    }
}