package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.io.BufferedReader
import java.io.File

class PInfo : AppCompatActivity() {

    lateinit var login_id :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinfo)

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var id = intent.getStringExtra("intent_userid")   //현재 id값 받아오기 위해 필요
        login_id = id.toString()

        setTitle("개인정보처리방침")

        val path = "/data/data/com.example.login/files/1.txt"
        var bufferReader : BufferedReader = File(path).bufferedReader()
        var inputString = bufferReader.use { it.readText() }

        var first_beforechange = findViewById(R.id.first) as TextView?
        first_beforechange?.setText(inputString)

        val path2 = "/data/data/com.example.login/files/2.txt"
        bufferReader = File(path2).bufferedReader()
        inputString = bufferReader.use { it.readText() }

        var second_beforechange = findViewById(R.id.second) as TextView?
        second_beforechange?.setText(inputString)

        val path3 = "/data/data/com.example.login/files/3.txt"
        bufferReader = File(path3).bufferedReader()
        inputString = bufferReader.use { it.readText() }

        var third_beforechange = findViewById(R.id.third) as TextView?
        third_beforechange?.setText(inputString)

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


