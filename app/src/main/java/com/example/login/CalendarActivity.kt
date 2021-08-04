package com.example.login

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class CalendarActivity : AppCompatActivity() {
    val ONE_DAY : Int = 24 * 60 * 60 * 1000 //Millisecond로 바꾼 하루(24시간)
    lateinit var myHelper: DiaryDBHelper //DiaryDBHelper 클래스 변수
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수
    var isExistBlank = false //빈칸이 있는지 확인
    lateinit var login_id : String

    val calendar: Calendar = Calendar.getInstance()   // 캘린더 객체 생성(오늘 날짜로 초기화됨)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        var calendarView : CalendarView = findViewById(R.id.calendarView) //달력
        var contentEditText : EditText = findViewById(R.id.contentEditText) //입력창
        var doneFab : FloatingActionButton = findViewById(R.id.doneFab) //저장버튼
        var tv_date : TextView = findViewById(R.id.tv_date)
        var tv_diary : TextView = findViewById(R.id.tv_diary)
        var tv_result : TextView = findViewById(R.id.tv_result)

        var id = intent.getStringExtra("intent_userid")
        login_id = id.toString()

        // 캘린더 뷰의 날짜를 선택했을 때 캘린더 객체에 설정
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var selectedDate = year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString() //선택 날짜 저장
            var strdate = selectedDate
            tv_date.text = strdate
            tv_result.text = getDday(year, month, dayOfMonth)

            myHelper = DiaryDBHelper(this)
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor

            cursor = sqlDB.rawQuery("SELECT * FROM user_diary_test where date='"+selectedDate+"';", null)

            if (cursor.moveToFirst()) { //저장된 항목 보여주기
                var strcontent = cursor.getString(cursor.getColumnIndex("content")).toString()
                tv_diary.text = strcontent
            } else {
                tv_diary.text = ""
            }
            cursor.close()

            // 쓰기 writableDB 작업
            doneFab.setOnClickListener {
                val content = contentEditText.text.toString()

                // 유저가 항목을 다 채우지 않았을 경우
                if (content.isBlank()) {
                    isExistBlank = true
                    Log.i("write_diary", "빈칸 인식")
                } else {
                    isExistBlank = false
                }

                if (isExistBlank == false) { //빈 칸이 없으면 diaryDB의 user_diary 테이블에 저장
                    sqlDB = myHelper.writableDatabase
                    sqlDB.execSQL(
                        "INSERT INTO user_diary_test ('userid','content','date') VALUES ('"
                                + id.toString() + "','"
                                + content.toString() + "','"
                                + selectedDate.toString() + "');"
                    )
                    sqlDB.close()
                    Toast.makeText(applicationContext, "저장되었습니다.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, CalendarActivity::class.java)
                    intent.putExtra("intent_userid", id)
                    startActivity(intent)
                } else {
                    if (isExistBlank) {   //작성하지 않으면
                        Toast.makeText(applicationContext, "빈칸입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //D-day 구하는 함수
    fun getDday(a_year : Int , a_monthOfYear: Int, a_dayOfMonth: Int): String {
        var ddayCalendar: Calendar = Calendar.getInstance()
        ddayCalendar.set(a_year, a_monthOfYear, a_dayOfMonth)

        var dday: Long = ddayCalendar.timeInMillis / ONE_DAY
        var today: Long = Calendar.getInstance().timeInMillis / ONE_DAY
        var result: Long = dday - today

        var strFormat: String

        if (result > 0) {
            strFormat = "D-%d"
        } else if (result.compareTo(0) == 0) {
            strFormat = "D-Day"
        } else {
            result *= -1
            strFormat = "D+%d"
        }

        var strCount: String = (String.format(strFormat, result))
        return strCount
    }

    //홈화면에 쓰일 메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    //우측 상단 홈 메뉴 누를 시 HomeActivity로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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