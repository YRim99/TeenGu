package com.example.login
//회원정보 변경 화면

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Change_Profile : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: myDBHelper //myDBHelper 클래스 변수
    lateinit var change_pwd : Button  //비밀번호 변경 버튼
    lateinit var change_nickname : Button    //별명 변경 버튼
    lateinit var change_finish : Button  //모두 변경완료 -> 클릭시 MainActivity로 이동
    lateinit var new_pwd : EditText     // 새로운 비밀번호 입력
    lateinit var new_nickname : EditText  //새로운 별명 입력
    lateinit var login_id :String // 로그인 아이디

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle("회원정보 수정")

        change_pwd = findViewById(R.id.change_pwd)
        change_nickname = findViewById(R.id.change_nickname)
        change_finish = findViewById(R.id.change_finish)
        new_pwd = findViewById(R.id.new_pwd)
        new_nickname = findViewById(R.id.new_nickname)

        var id = intent.getStringExtra("intent_userid")   //현재 id값 받아오기 위해 필요
        login_id = id.toString()

        myHelper = myDBHelper(this)
        sqlDB = myHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM memberDB WHERE memID='"+id.toString()+"';",null)

        var email : String //이메일 받아올 변수 (이메일 변경 X)
        var age : Int //나이 받아올 변수(나이 변경 X)
        var pwd : String // 비밀번호 받아올 변수 (변경 O)
        var nickname : String  // 변경할 닉네임

        if(cursor != null && cursor.moveToNext()) {
            do {
                email = cursor.getString(4)
                age = cursor.getInt(3)

                var profile_id = findViewById(R.id.id) as TextView?
                profile_id?.setText(id.toString())                       //id값은 변경안되므로 그대로 입력시키기

                var profile_email = findViewById(R.id.email) as TextView?
                profile_email?.setText(email.toString())                    // email값은 변경안되므로 그대로 입력

                var profile_age = findViewById(R.id.age) as TextView?
                profile_age?.setText(age.toString())                     // age값은 변경안되므로 그대로 입력

            } while (cursor.moveToNext())
        }

        change_pwd.setOnClickListener {
            pwd = new_pwd?.text.toString()
            sqlDB.execSQL("UPDATE memberDB SET memPW ='"+pwd+"' WHERE memID='"+id.toString()+"';")
            Toast.makeText(applicationContext, "비밀번호 변경완료", Toast.LENGTH_SHORT).show()
        }

        change_nickname.setOnClickListener {
            nickname = new_nickname?.text.toString()
            sqlDB.execSQL("UPDATE memberDB SET memNAME ='"+nickname+"' WHERE memID='"+id.toString()+"';")
            Toast.makeText(applicationContext, "별명 변경완료", Toast.LENGTH_SHORT).show()
        }

        change_finish.setOnClickListener {
            Toast.makeText(applicationContext, "회원정보 변경이 완료되어 로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intent_userid", id)
            startActivity(intent)
        }
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