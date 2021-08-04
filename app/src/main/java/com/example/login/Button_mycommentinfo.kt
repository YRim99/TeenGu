package com.example.login

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mycommentinfo)

        val id = intent.getStringExtra("intent_userid")
        login_id = id.toString()

        btn_reply_title = findViewById(R.id.btn_reply_title)
        btn_reply_content = findViewById(R.id.btn_reply_content)

        var comment_title = intent.getStringExtra("intent_text_title")
        var board_title = intent.getStringExtra("intent_board_title")
        btn_reply_title.text = comment_title

        communityDBHelper = communityDBHelper(this)
        sqlDB = communityDBHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM "+ board_title+" where board_title='"+comment_title+"';", null)

        var num : Int = 0
        while(cursor.moveToNext()) {
            var reply_text_content =
                cursor.getString(cursor.getColumnIndex("board_content")).toString()

            btn_reply_content.text = reply_text_content
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            //우측 상단 홈 메뉴 누를 시 HomeActivity로 이동
            R.id.action_home ->{
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("intent_userid",login_id)
                startActivity(intent)

                return true
            }
            //우측 상단 채팅 메뉴 누를시 ChatActivity로 이동
            R.id.action_chat ->{
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("intent_userid", login_id)
                startActivity(intent)

                return true
            }

            //마이페이지로 이동
            R.id.action_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)
                intent.putExtra("intent_userid", login_id)
                startActivity(intent)

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}