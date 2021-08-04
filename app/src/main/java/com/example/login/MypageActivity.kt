package com.example.login

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {

    lateinit var mypost : ImageButton
    lateinit var mycomment : ImageButton
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var myHelper: myDBHelper //myDBHelper 클래스 변수

    lateinit var writer : String //작성자 변수
    lateinit var login_id :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        setTitle("마이페이지")
        mypost = findViewById(R.id.mypost)
        mycomment = findViewById(R.id.mycomment)

        //'**님 안녕하세요'
        var id = intent.getStringExtra("intent_userid")

        writer = id.toString()
        login_id = writer

        myHelper = myDBHelper(this)
        sqlDB = myHelper.readableDatabase

        var cursor : Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM memberDB WHERE memID='"+id.toString()+"';",null)

        var name : String

        if(cursor != null && cursor.moveToNext()) {
            do {
                name = cursor.getString(2)
                var welcome_string = findViewById(R.id.welcome_string) as TextView?
                welcome_string?.setText(name + "님 안녕하세요")
            } while (cursor.moveToNext())
        }

        //프로필 변경
        val profile_change_click_me = findViewById(R.id.profile_change) as TextView
        profile_change_click_me.setOnClickListener {
            val intent = Intent(this,Change_Profile::class.java)
            intent.putExtra("id",writer)
            startActivity(intent)
        }

        //회원탈퇴
        val exit_click_me = findViewById(R.id.exit) as TextView
        exit_click_me.setOnClickListener {
            val builder2 = AlertDialog.Builder(this)
            builder2.setTitle("회원탈퇴")
            builder2.setMessage("정말로 탈퇴하시겠습니까?")
            builder2.setPositiveButton(
                "확인",
                {dialogInterface:DialogInterface?, i:Int ->
                    sqlDB.execSQL("DELETE FROM memberDB WHERE memID='"+writer+"';")
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                })
            builder2.setNegativeButton(
                "취소",
                { dialogInterface:DialogInterface?, i:Int ->

                })
            builder2.show()

        }


        //로그아웃
        val logout_click_me = findViewById(R.id.logout) as TextView
        logout_click_me.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //개인정보처리방침
        val inforsec_clicke_me = findViewById(R.id.infosec) as TextView
        inforsec_clicke_me.setOnClickListener {
            val intent = Intent(this, PInfo::class.java)
            startActivity(intent)
        }

        //버전정보
        val version_click_me = findViewById(R.id.version) as TextView
        version_click_me.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("버전 정보")
            builder.setMessage("1.0")
            builder.setPositiveButton(
                "확인",
                {dialogInterface:DialogInterface?, i:Int ->

                })
            builder.show()
        }


        //내가 작성한 글
        mypost.setOnClickListener {
            val intent = Intent(this, Button_mytext::class.java)
            intent.putExtra("text_writer",writer)
            startActivity(intent)
        }

        //내가 댓글단 글
        mycomment.setOnClickListener {
            val intent = Intent(this, Button_mycomment::class.java)
            intent.putExtra("text_writer",writer)
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
                var id = intent.getStringExtra("intent_userid")
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("intent_userid",id)
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
        }
        return super.onOptionsItemSelected(item)
    }
}