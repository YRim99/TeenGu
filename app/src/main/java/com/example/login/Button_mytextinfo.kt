package com.example.login

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Button_mytextinfo : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: communityDBHelper

    lateinit var btn_title : TextView
    lateinit var btn_content : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mytextinfo)
        setTitle("내가 쓴 글 자세히")

        btn_title = findViewById(R.id.btn_title)
        btn_content = findViewById(R.id.btn_content)

        var text_title = intent.getStringExtra("title").toString()
        var text_content = intent.getStringExtra("content").toString()

        btn_title.text = text_title
        btn_content.text = text_content
    }
}