package com.example.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar

class Button_mytext : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var communityDBHelper: communityDBHelper
    lateinit var myDBHelper: myDBHelper
    lateinit var layout: LinearLayout

    lateinit var text_writer : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttonmytext)
        setTitle("내가 쓴 글 목록")

        val intent = intent
        text_writer = intent.getStringExtra("text_writer").toString()
        layout = findViewById(R.id.personnel_text)

        communityDBHelper = communityDBHelper(this)
        sqlDB = communityDBHelper.readableDatabase

        var cursor : Cursor

        //자유게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_info WHERE userid='"+text_writer+"';",null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "자유게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.setPadding(50,30,50,30)
            layout_list.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_list.id = num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.textSize = 15f
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //질문게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_QA WHERE userid='"+text_writer+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "질문게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.id=num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 30f
            title.setBackgroundColor(Color.GREEN)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //마켓게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_market WHERE userid='"+text_writer+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "마켓게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.id=num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 30f
            title.setBackgroundColor(Color.GREEN)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //광고게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_ad WHERE userid='"+text_writer+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "광고게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.id=num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 30f
            title.setBackgroundColor(Color.GREEN)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.maxLines = 2
            content.setEllipsize(TextUtils.TruncateAt.END)
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }
        cursor.close()
        sqlDB.close()
        communityDBHelper.close()



    }
}

