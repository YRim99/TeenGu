package com.example.login
//회원가입 화면
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class JoinActivity : AppCompatActivity() {
    val TAG : String = "Join"
    var isExistBlank = false //빈칸이 있는지 확인
    lateinit var myHelper: myDBHelper //myDBHelper 클래스 변수
    lateinit var btnCheck : Button
    lateinit var btnRegister : Button
    lateinit var sqlDB : SQLiteDatabase //SQLiteDatabase 클래스 변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        var join_id : EditText? = findViewById(R.id.join_id)!!
        var join_password : EditText? = findViewById(R.id.join_password)!!
        var join_name : EditText? = findViewById(R.id.join_name)!!
        var join_age : EditText? = findViewById(R.id.join_age)!!
        var join_email : EditText? = findViewById(R.id.join_email)!!
        btnCheck = findViewById(R.id.btnCheck)
        btnRegister = findViewById(R.id.btnRegister)
        myHelper = myDBHelper(this)

        btnCheck.setOnClickListener { //아이디 중복확인
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor = sqlDB.rawQuery("SELECT memID from memberDB where memID='"+ join_id?.text.toString()+"';", null)

            cursor.moveToFirst();
            if (cursor.count > 0) {
                Toast.makeText(applicationContext, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                join_id?.setText("")
            } else {
                Toast.makeText(applicationContext, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            sqlDB.close()
        }


        btnRegister.setOnClickListener { //가입신청 버튼을 클릭
            val id = join_id?.text.toString()
            val pw = join_password?.text.toString()
            val name = join_name?.text.toString()
            val age = join_age?.text.toString()
            val email = join_email?.text.toString()

            // 유저가 항목을 다 채우지 않았을 경우
            if(id.isBlank() || pw.isBlank() || name.isBlank() || age.isBlank() || email.isBlank()){
                isExistBlank = true
                Log.i(TAG, "빈칸 인식")
            } else{
                isExistBlank = false
            }

            if(isExistBlank == false) { //빈 칸이 없으면 memberDB에 저장
                sqlDB = myHelper.writableDatabase
                sqlDB.execSQL(
                    "INSERT INTO memberDB VALUES ('"
                            + join_id?.text.toString() + "','"
                            + join_password?.text.toString() + "','"
                            + join_name?.text.toString() + "',"
                            + join_age?.text.toString() + ",'"
                            + join_email?.text.toString() + "');"
                )
                sqlDB.close()
                Toast.makeText(applicationContext, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show()

                //회원가입 후 MainActivity 로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                if(isExistBlank){   // 작성 안한 항목이 있을 경우
                    dialog("blank")
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                //toolbar의 back키 눌렀을 때 동작
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 회원가입 실패시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)

        // 작성 안한 항목이 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "다이얼로그")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}