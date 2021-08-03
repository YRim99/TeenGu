package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class CommunityActivity : AppCompatActivity() {
    lateinit var myHelper: communityDBHelper //communityDBHelper 클래스 변수
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        var id = intent.getStringExtra("intent_userid") //주원 여기 추가했어
        login_id = id.toString()
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show() //값 잘 넘어오는지 테스트하는거, 나중에 지우기

        //프래그먼트 생성
        val mfragment: BoardFragment = BoardFragment()
        //프래그먼트를 액티비티에 넣기 위한 트랜잭션 객체 생성
        val transaction = supportFragmentManager.beginTransaction()
        //액티비티에 넣을 <프래그먼트>와 <넣을 위치>를 트랜잭션에 추가
        transaction.add(R.id.frameLayout, mfragment)


        //주원 이 문단 추가했어
        val bundle = Bundle()
        bundle.putString("intent_userid",id) //HomeActivity에서 넘어온 userid 값을 BoardFragment에 전달
        mfragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
        transaction.replace(R.id.frameLayout, mfragment)
        transaction.commit()
    }

    //홈화면에 쓰일 메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    //우측 상단 홈 메뉴 누를 시 HomeActivity로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home ->{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                return true
            }
            //우측 상단 채팅 메뉴 누를시 ChatActivity로 이동
            R.id.action_chat ->{
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("user_id", login_id)
                startActivity(intent)

                return true
            }

            //마이페이지로 이동
            R.id.action_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)

                intent.putExtra("user_id", login_id)
                startActivity(intent)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}