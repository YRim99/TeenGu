package com.example.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*

class BoardViewActivity : AppCompatActivity() {
    var isExistBlank = false //댓글 빈칸 확인
    lateinit var myHelper: replyDBHelper
    lateinit var sqlDB: SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var reply_reg : ImageButton
    lateinit var title : TextView //게시글 제목
    lateinit var content : TextView //게시글 내용
    lateinit var layout : LinearLayout //댓글 목록
    lateinit var reply : EditText
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
        reply = findViewById(R.id.replyEdit)

        //글 목록에서 불러온 정보
        var intent_userid = intent.getStringExtra("intent_userid")
        var intent_table_name = intent.getStringExtra("intent_table_name")
        var intent_title = intent.getStringExtra("intent_title")
        var intent_content = intent.getStringExtra("intent_content")

        login_id = intent_userid.toString()

        //게시글 보여주기
        title.text = intent_title
        content.text = intent_content

        //댓글 달기 기능
        reply_reg = findViewById(R.id.reply_reg)
        myHelper = replyDBHelper(this)

        // 쓰기 writableDB 작업
        reply_reg.setOnClickListener {
            val content = reply?.text.toString()

            // 유저가 항목을 채우지 않았을 경우
            if (content.isBlank()) {
                isExistBlank = true
                Log.i("write", "빈칸 인식")
            } else {
                isExistBlank = false
            }

            if (isExistBlank == false) { //빈 칸이 없으면 replyDB에 저장
                sqlDB = myHelper.writableDatabase
                sqlDB.execSQL(
                    "INSERT INTO table_reply ('userid','table_name','board_title','reply') VALUES ('"
                            + intent_userid.toString() + "','"
                            + intent_table_name.toString() + "','"
                            + intent_title.toString() + "','"
                            + content.toString() + "');"
                )
                sqlDB.close()

                val intent = Intent(this, BoardViewActivity::class.java)
                intent.putExtra("intent_userid",intent_userid)
                intent.putExtra("intent_table_name",intent_table_name)
                intent.putExtra("intent_title",intent_title)
                intent.putExtra("intent_content",intent_content)
                startActivity(intent)

            } else {
                if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                    Toast.makeText(applicationContext, "빈칸입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //댓글 목록보기
        myHelper = replyDBHelper(this)
        sqlDB = myHelper.readableDatabase

        layout = findViewById(R.id.reply_lo)
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM table_reply where board_title='"+intent_title+"';", null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var reply_userid = cursor.getString(cursor.getColumnIndex("userid")).toString()
            var reply_content = cursor.getString(cursor.getColumnIndex("reply")).toString()

            var layout_item : LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(20,10,20,10)
            layout_item.id = num
            layout_item.setBackgroundColor(Color.parseColor("#DCDCDC")) //댓글 디자인

            var save_userid : TextView = TextView(this)
            save_userid.textSize = 13F
            save_userid.text = reply_userid
            layout_item.addView(save_userid)

            var save_content : TextView = TextView(this)
            save_content.textSize = 13F
            save_content.text = reply_content
            layout_item.addView(save_content)

            layout.addView(layout_item)
            num++
        }

        cursor.close()
        sqlDB.close()
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