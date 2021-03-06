package com.example.login
//자유 게시판 목록 화면
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Board_infoActivity : AppCompatActivity() {
    lateinit var myHelper: communityDBHelper //communityDBHelper 클래스 변수
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var layout : LinearLayout
    lateinit var writeButton : Button
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_info)

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var id = intent.getStringExtra("intent_userid")
        login_id = id.toString()

        myHelper = communityDBHelper(this)
        sqlDB = myHelper.readableDatabase

        layout = findViewById(R.id.board_lo)
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_info;", null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var strtitle = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var strcontent = cursor.getString(cursor.getColumnIndex("board_content")).toString()

            var layout_item : LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(20,10,20,10)
            layout_item.id = num
            layout_item.setBackgroundColor(Color.parseColor("#DCDCDC"))

            var save_title : TextView = TextView(this)
            save_title.textSize = 20f
            save_title.text = strtitle
            save_title.setTextColor(Color.BLACK)
            layout_item.addView(save_title)

            var save_content : TextView = TextView(this)
            save_content.textSize = 15f
            save_content.text = strcontent
            save_content.maxLines = 1
            save_content.ellipsize = TextUtils.TruncateAt.END
            layout_item.addView(save_content)


            layout_item.setOnClickListener{
                val intent = Intent(this, BoardViewActivity::class.java)
                intent.putExtra("intent_userid",id)
                intent.putExtra("intent_table_name","table_board_info")
                intent.putExtra("intent_title",save_title.text)
                intent.putExtra("intent_content",save_content.text)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++
        }

        cursor.close()
        sqlDB.close()

        writeButton = findViewById(R.id.writeButton)

        writeButton.setOnClickListener {
            //글 쓰기 액티비티로 이동
            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("intent_userid",id)
            intent.putExtra("intent_table_name","table_board_info")
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
                intent.putExtra("intent_userid", login_id)
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