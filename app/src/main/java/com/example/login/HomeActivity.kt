package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast



class HomeActivity : AppCompatActivity() {
    lateinit var btnCommunity : Button
    lateinit var btnInformation : Button
    lateinit var btnHospital : Button
    lateinit var btnCalendar : Button
    lateinit var btnInfomation : Button

    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var id = intent.getStringExtra("intent_userid")
        login_id = id.toString()

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show() //값 잘 넘어오는지 테스트하는거, 나중에 지우기


        btnCommunity = findViewById(R.id.btnCommunity)
        btnInformation = findViewById(R.id.btnInfomation)
        btnHospital = findViewById(R.id.btnHostpital)
        btnCalendar = findViewById(R.id.btnCalendar)
        btnInfomation = findViewById(R.id.btnInfomation)

        //커뮤니티 버튼 -> CommunityActivity 로 이동
        btnCommunity.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            intent.putExtra("intent_userid", id)
            startActivity(intent)
        }

        //캘린더 버튼 -> CalendarActivity로 이동
        btnCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            intent.putExtra("intent_userid", id)
            startActivity(intent)
        }

        //정보버튼 -> teenguInfo.kt
        btnInfomation.setOnClickListener {
            val intent = Intent(this, teenguInfo::class.java)
            intent.putExtra("intent_userid", id)
            startActivity(intent)
        }

        //산부인과 버튼 -> MapsActivity.kt
        btnHospital.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("intent_userid", id)
            startActivity(intent)
        }
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