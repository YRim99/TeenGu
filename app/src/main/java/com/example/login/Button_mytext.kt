package com.example.login
//내가 쓴 글 목록 보여주는 화면
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class Button_mytext : AppCompatActivity() {

    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    lateinit var communityDBHelper: communityDBHelper
    lateinit var layout: LinearLayout
    lateinit var login_id :String // 로그인 아이디

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttonmytext)
        setTitle("내가 쓴 글 목록")

        // 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        var intent_userid = intent.getStringExtra("intent_userid").toString()
        login_id = intent_userid
        layout = findViewById(R.id.personnel_text)

        communityDBHelper = communityDBHelper(this)
        sqlDB = communityDBHelper.readableDatabase

        var cursor : Cursor

        //자유게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_info WHERE userid='"+intent_userid+"';",null)

        var num : Int = 0
        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "자유게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.setPadding(50,30,50,30)
            layout_list.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_list.id = num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.textSize = 15f
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                intent.putExtra("intent_userid",intent_userid)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //질문게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_QA WHERE userid='"+intent_userid+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "질문게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.setPadding(50,30,50,30)
            layout_list.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_list.id = num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.textSize = 15f
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                intent.putExtra("intent_userid", intent_userid)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //마켓게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_market WHERE userid='"+intent_userid+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "마켓게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.setPadding(50,30,50,30)
            layout_list.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_list.id = num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.textSize = 15f
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                intent.putExtra("intent_userid", intent_userid)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }

        //광고게시판
        cursor = sqlDB.rawQuery("SELECT * FROM table_board_ad WHERE userid='"+intent_userid+"';",null)

        while(cursor.moveToNext()){
            var text_title = cursor.getString(cursor.getColumnIndex("board_title")).toString()
            var text_content = cursor.getString(cursor.getColumnIndex("board_content")).toString()
            var text_board = "광고게시판"

            //텍스트뷰가 들어있는 레이아웃 생성
            var layout_list:LinearLayout = LinearLayout(this)
            layout_list.orientation=LinearLayout.VERTICAL
            layout_list.setPadding(50,30,50,30)
            layout_list.setBackgroundColor(Color.parseColor("#DCDCDC"))
            layout_list.id = num

            var title:TextView = TextView(this)
            title.text=text_title
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            layout_list.addView(title)

            var content : TextView = TextView(this)
            content.text = text_content
            content.textSize = 15f
            layout_list.addView(content)

            var boardname : TextView = TextView(this)
            boardname.text = text_board
            layout_list.addView(boardname)

            layout_list.setOnClickListener {
                val intent = Intent(this, Button_mytextinfo::class.java)
                intent.putExtra("title", text_title)
                intent.putExtra("content", text_content)
                intent.putExtra("boardname", text_board)
                intent.putExtra("intent_userid", intent_userid)
                startActivity(intent)
            }

            layout.addView(layout_list)
            num++

        }
        cursor.close()
        sqlDB.close()
        communityDBHelper.close()
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

