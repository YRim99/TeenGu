package com.example.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class myDBHelper (context : Context) : SQLiteOpenHelper(context, "memberDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) { //테이블 생성
        db!!.execSQL("CREATE TABLE memberDB (memID CHAR(20) PRIMARY KEY, memPW CHAR(20), memNAME CHAR(20), memAGE INTEGER, memEMAIL CHAR(20));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {//테이블 삭제후 재생성
        db!!.execSQL("DROP TABLE IF EXISTS memberDB")
        onCreate(db)
    }

}