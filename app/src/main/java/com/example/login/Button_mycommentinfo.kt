package com.example.login
//내가 단 댓글 상세보기 화면
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.database.sqlite.SQLiteDatabase as SQLiteDatabase

class Button_mycommentinfo : AppCompatActivity() {
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: replyDBHelper
    lateinit var reply_title : TextView
    lateinit var reply_content : TextView

    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mycommentinfo)

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("intent_userid")
        login_id = id.toString()

        reply_title = findViewById(R.id.btn_reply_title)
        reply_content = findViewById(R.id.btn_reply_content)

        var comment_title = intent.getStringExtra("title")
        reply_title.text = comment_title

        myHelper = replyDBHelper(this)
        sqlDB = myHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM table_reply where board_title='"+comment_title+"';", null)


        while(cursor.moveToNext()) {
            var reply_text_content = cursor.getString(cursor.getColumnIndex("reply")).toString()
            Toast.makeText(applicationContext, reply_text_content, Toast.LENGTH_LONG).show()
            reply_content.text = reply_text_content
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
            // 이전 페이지로 이동
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}