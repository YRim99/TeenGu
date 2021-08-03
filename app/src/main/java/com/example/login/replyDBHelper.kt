package com.example.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class replyDBHelper(context: Context) : SQLiteOpenHelper (context, "replyDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE table_reply (num Integer PRIMARY KEY AUTOINCREMENT, userid CHAR(20), table_name CHAR(20), board_title CHAR(20), reply VARCHAR(200));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS table_reply")
        onCreate(db)
    }
}