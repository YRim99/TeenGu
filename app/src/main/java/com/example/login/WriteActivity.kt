package com.example.login

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class WriteActivity : AppCompatActivity() {
    var isExistBlank = false //빈칸이 있는지 확인
    lateinit var myHelper: communityDBHelper
    lateinit var write_button: Button
    lateinit var sqlDB: SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        var id = intent.getStringExtra("intent_userid")
        var tableName = intent.getStringExtra("intent_table_name")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show() //값 잘 넘어오는지 테스트하는거, 나중에 지우기

        login_id = id.toString()

        var write_title: EditText? = findViewById(R.id.write_title)!!
        var write_content: EditText? = findViewById(R.id.write_content)!!
        write_button = findViewById(R.id.write_button)
        myHelper = communityDBHelper(this)

        // 쓰기 writableDB 작업
        write_button.setOnClickListener {
            val title = write_title?.text.toString()
            val content = write_content?.text.toString()

            // 유저가 항목을 다 채우지 않았을 경우
            if (title.isBlank() || content.isBlank()) {
                isExistBlank = true
                Log.i("write", "빈칸 인식")
            } else {
                isExistBlank = false
            }

            if (isExistBlank == false) { //빈 칸이 없으면 memberDB에 저장
                sqlDB = myHelper.writableDatabase
                sqlDB.execSQL(
                    "INSERT INTO " + tableName + "('userid','board_title','board_content') VALUES ('"
                            + id.toString() + "','"
                            + title.toString() + "','"
                            + content.toString() + "');"
                )
                sqlDB.close()
                Toast.makeText(applicationContext, "글쓰기를 완료하였습니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, BoardViewActivity::class.java)
                intent.putExtra("intent_table_name",tableName)
                intent.putExtra("intent_title",title)
                intent.putExtra("intent_content",content)
                intent.putExtra("intent_userid",id)
                startActivity(intent)
            } else {
                if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                    Toast.makeText(applicationContext, "모든 항목을 작성해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
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
                //우측 상단 홈 메뉴 누를시 HomeActivity로 이동
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

            //우측 상단 마이페이지 메뉴 누를시 MypageActivity로 이동
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