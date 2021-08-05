package com.example.login

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class Button_mytextinfo : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: communityDBHelper

    lateinit var btn_title : TextView
    lateinit var btn_content : TextView
    lateinit var login_id :String // 로그인 아이디

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_mytextinfo)
        setTitle("내가 쓴 글 자세히")

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var id = intent.getStringExtra("intent_userid")   //현재 id값 받아오기 위해 필요
        login_id = id.toString()

        btn_title = findViewById(R.id.btn_title)
        btn_content = findViewById(R.id.btn_content)

        var intent_userid = intent.getStringExtra("intent_userid").toString()
        Toast.makeText(applicationContext, intent_userid, Toast.LENGTH_SHORT).show()
        var text_title = intent.getStringExtra("title").toString()
        Toast.makeText(applicationContext, text_title, Toast.LENGTH_LONG).show()
        var text_content = intent.getStringExtra("content").toString()
        Toast.makeText(applicationContext, text_content, Toast.LENGTH_LONG).show()

        btn_title.text = text_title
        btn_content.text = text_content

        val intent = Intent(this, MypageActivity::class.java)
        intent.putExtra("intent_userid", intent_userid)
    }
    //홈화면에 쓰일 메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mypage, menu)
        return true
    }

    //우측 상단 홈 메뉴 누를 시 HomeActivity로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
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
            // 이전 페이지로 이동
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}