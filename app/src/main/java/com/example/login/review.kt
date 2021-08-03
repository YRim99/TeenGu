package com.example.login

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.login.DBManager
import com.example.login.R


class review : AppCompatActivity() {

    lateinit var username : TextView
    lateinit var reviewText : EditText
    lateinit var regButton : Button
    lateinit var review : LinearLayout

    // db 객체 선언 및 연결
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        dbManager = DBManager(this, "map", null, 1)

        username = findViewById(R.id.username)
        reviewText = findViewById(R.id.reviewText)
        regButton = findViewById(R.id.regButton)
        review = findViewById(R.id.review)

        // 병원 이름 받아오기
        val hosName = intent.getStringExtra("hosName")

        // 후기 리스트 출력
        sqlitedb = dbManager.readableDatabase

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM review where hosName ='"+hosName+"';", null)

        var num : Int=0
        while(cursor.moveToNext()){

            if(cursor == null)
                Toast.makeText(applicationContext, "리뷰가 없습니다.", Toast.LENGTH_SHORT).show()

            var str_username = cursor.getString(cursor.getColumnIndex("username")).toString()
            var str_userRev = cursor.getString(cursor.getColumnIndex("review")).toString()

            var layout_items:LinearLayout = LinearLayout(this)
            layout_items.orientation = LinearLayout.VERTICAL
            layout_items.setPadding(20,10,20,10)
            layout_items.id = num
            layout_items.setTag(str_username)

            var tvName: TextView = TextView(this)
            tvName.text = str_username
            tvName.textSize = 15F
            layout_items.addView(tvName)

            var tvreview: TextView = TextView(this)
            tvreview.text = str_userRev
            tvreview.textSize = 15F
            layout_items.addView(tvreview)

            review.addView(layout_items)
            num++
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 후기 작성
        regButton.setOnClickListener {
            //사용자 이름 받아오기
            val username2 = intent.getStringExtra("intent_userid")

            var str_review = reviewText.text.toString()

            username.text=username2

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO review VALUES('$username2', $hosName, '$str_review');")
            Toast.makeText(applicationContext, "후기가 작성되었습니다.", Toast.LENGTH_SHORT).show()
            sqlitedb.close()
        }
    }
}