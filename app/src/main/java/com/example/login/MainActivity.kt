package com.example.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var myHelper: myDBHelper //myDBHelper 클래스 변수
    val TAG : String = "Login"
    var isExistBlank = false //빈칸이 있는지 확인
    lateinit var edtID : EditText
    lateinit var edtPassword : EditText
    lateinit var loginButton : Button
    lateinit var joinButton : Button
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtID = findViewById(R.id.edtID)
        edtPassword = findViewById(R.id.edtPassword)
        loginButton = findViewById(R.id.loginButton)
        joinButton = findViewById(R.id.joinButton)
        myHelper = myDBHelper(this)

        //로그인 -> DB에 있는지? 조회 -> Toast Message(환영합니다, 해당 아이디는 존재하지 않습니다)
        loginButton.setOnClickListener {
            val id = edtID?.text.toString()
            val pw = edtPassword?.text.toString()

            // 유저가 항목을 다 채우지 않았을 경우
            if(id.isBlank() || pw.isBlank()){
                isExistBlank = true
                Toast.makeText(applicationContext, "모든 칸을 작성해주세요", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "빈칸 인식")
            } else{
                isExistBlank = false
            }

            if(isExistBlank == false) { //빈 칸이 없으면 memberDB에서 조회
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor
                cursor = sqlDB.rawQuery("SELECT * FROM memberDB WHERE memID='"+edtID.text.toString()+"' AND memPW='"+edtPassword.text.toString()+"';",null)

                if(cursor != null && cursor.moveToNext()){
                    do {
                        Toast.makeText(applicationContext, cursor.getString(2) + "님 환영합니다", Toast.LENGTH_SHORT).show()
                    } while (cursor.moveToNext())

                    cursor.close()
                    sqlDB.close()

                    //로그인 후 HomeActivity 로 이동
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("intent_userid",id)
                    startActivity(intent)

                } else {
                    Toast.makeText(applicationContext, "아이디 혹은 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    cursor.close()
                    sqlDB.close()
                }
            }
        }

        //회원가입 버튼 -> JoinActivity 로 넘어가도록
        joinButton.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}