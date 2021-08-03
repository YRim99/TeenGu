package com.example.login

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import android.database.sqlite.SQLiteDatabase as SQLiteDatabase

class Button_mycommentinfo : AppCompatActivity() {
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: replyDBHelper

    lateinit var btn_reply_title : TextView
    lateinit var btn_reply_content : TextView
    lateinit var communityDBHelper: communityDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mycommentinfo)

        btn_reply_title = findViewById(R.id.btn_reply_title)

        var comment_title = intent.getStringExtra("intent_text_title")
        var board_title = intent.getStringExtra("intent_board_title")
        btn_reply_title.text = comment_title

        communityDBHelper = communityDBHelper(this)
        sqlDB = communityDBHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM"+ board_title+" where board_title='"+comment_title+"';", null)

        var num : Int = 0
        while(cursor.moveToNext()) {
            var reply_text_content =
                cursor.getString(cursor.getColumnIndex("board_content")).toString()

            btn_reply_content.text = reply_text_content
        }




    }
}