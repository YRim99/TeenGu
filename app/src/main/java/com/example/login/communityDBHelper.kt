package com.example.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//주원 여기 수정했어
class communityDBHelper (context : Context) : SQLiteOpenHelper(context, "communityDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) { //테이블 생성
        db!!.execSQL("CREATE TABLE table_board_info (userid CHAR(20), board_title CHAR(20) PRIMARY KEY, board_content VARCHAR(200));") //자유
        db!!.execSQL("CREATE TABLE table_board_QA (userid CHAR(20), board_title CHAR(20) PRIMARY KEY, board_content VARCHAR(200));") //질문
        db!!.execSQL("CREATE TABLE table_board_market (userid CHAR(20), board_title CHAR(20) PRIMARY KEY, board_content VARCHAR(200));") //마켓
        db!!.execSQL("CREATE TABLE table_board_ad (userid CHAR(20), board_title CHAR(20) PRIMARY KEY, board_content VARCHAR(200));") //광고
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {//테이블 삭제후 재생성
        db!!.execSQL("DROP TABLE IF EXISTS table_board_info")
        db!!.execSQL("DROP TABLE IF EXISTS table_board_QA")
        db!!.execSQL("DROP TABLE IF EXISTS table_board_marekt")
        db!!.execSQL("DROP TABLE IF EXISTS table_board_ad")
        onCreate(db)
    }

}