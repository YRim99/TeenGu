package com.example.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class Button_mycomment : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var communityDBHelper: communityDBHelper
    lateinit var replyDBHelper: replyDBHelper
    lateinit var layout: LinearLayout
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mycomment)
        setTitle("댓글 남긴 글 목록")

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        var intent_userid = intent.getStringExtra("intent_userid").toString()
        Toast.makeText(applicationContext, intent_userid, Toast.LENGTH_SHORT).show()
        layout = findViewById(R.id.personnel_reply)

        replyDBHelper = replyDBHelper(this)
        sqlDB = replyDBHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM table_reply where userid='"+intent_userid+"';", null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var reply_content = cursor.getString(cursor.getColumnIndex("reply")).toString()
            var reply_texttitle = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var reply_board_title = cursor.getString(cursor.getColumnIndex("table_name")).toString()

            //textView가 들어있는 layout 생성
            var layout_item : LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(50,30,50,30)
            layout_item.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_item.id = num

            var text_title : TextView = TextView(this)
            text_title.text = reply_texttitle
            text_title.textSize = 20f
            text_title.setTextColor(Color.BLACK)
            layout_item.addView(text_title)

            var content : TextView = TextView(this)
            content.text = reply_content
            content.textSize = 15f
            layout_item.addView(content)

            var boardname : TextView = TextView(this)
            boardname.textSize = 11f
            if(reply_board_title == "table_board_info"){
                boardname.text = "자유게시판"
            } else if(reply_board_title == "table_board_qa"){
                boardname.text = "질문게시판"
            } else if(reply_board_title == "table_board_market"){
                boardname.text = "플리마켓"
            } else{
                boardname.text = "홍보게시판"
            }

            layout_item.addView(boardname)

            layout_item.setOnClickListener {
                val intent = Intent(this, Button_mycommentinfo::class.java)
                intent.putExtra("intent_userid", intent_userid)
                intent.putExtra("title",reply_texttitle)
                intent.putExtra("content", reply_content)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++
        }

        cursor.close()
        sqlDB.close()
        replyDBHelper.close()
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
            // 이전 페이지로 이동
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}