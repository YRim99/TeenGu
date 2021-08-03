package com.example.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class Button_mycomment : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var communityDBHelper: communityDBHelper
    lateinit var replyDBHelper: replyDBHelper
    lateinit var myDBHelper: myDBHelper
    lateinit var layout: LinearLayout

    lateinit var text_writer : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mycomment)

        val intent = intent
        text_writer = intent.getStringExtra("text_writer").toString()
        layout = findViewById(R.id.personnel_reply)

        replyDBHelper = replyDBHelper(this)
        sqlDB = replyDBHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM table_reply where userid='"+text_writer+"';", null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var reply_content = cursor.getString(cursor.getColumnIndex("reply")).toString()
            var reply_texttitle = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var reply_board_title = cursor.getString(cursor.getColumnIndex("table_name")).toString()

            //textView가 들어있는 layout 생성
            var layout_item : LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var text_title : TextView = TextView(this)
            text_title.text = reply_texttitle
            text_title.textSize = 20f
            text_title.setBackgroundColor(Color.GREEN)
            layout_item.addView(text_title)

            var content : TextView = TextView(this)
            content.text = reply_content
            layout_item.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = reply_board_title

            layout_item.setOnClickListener {
                val intent = Intent(this, Button_mycommentinfo::class.java)
                intent.putExtra("intent_text_title", reply_texttitle)
                intent.putExtra("intent_reply_content", reply_content)
                intent.putExtra("intent_board_title", reply_board_title)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++
        }

        cursor.close()
        sqlDB.close()
        replyDBHelper.close()
    }
}