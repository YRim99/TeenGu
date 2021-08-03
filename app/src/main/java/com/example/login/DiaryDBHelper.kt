package com.example.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DiaryDBHelper (context : Context) : SQLiteOpenHelper(context, "diaryDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) { //테이블 생성
        db!!.execSQL("CREATE TABLE user_diary (num Integer PRIMARY KEY AUTOINCREMENT, date TEXT, userid CHAR(20), content VARCHAR(200));")
        db!!.execSQL("CREATE TABLE user_diary_test (date TEXT PRIMARY KEY, userid CHAR(20), content VARCHAR(200));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {//테이블 삭제후 재생성
        db!!.execSQL("DROP TABLE IF EXISTS user_diary")
        onCreate(db)
    }

}