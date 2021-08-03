package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap


class ChatActivity: AppCompatActivity() {
    lateinit var btn_send : ImageButton
    lateinit var et_msg : EditText
    lateinit var lv_chating : ListView

    lateinit var arrayAdapter: ArrayAdapter<String>

    lateinit var login_id : String
    lateinit var str_name : String
    lateinit var str_msg : String
    lateinit var chat_user : String
    var reference : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("message")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        lv_chating = findViewById<ListView>(R.id.lv_chating)
        btn_send = findViewById<ImageButton>(R.id.btn_send)
        et_msg = findViewById<EditText>(R.id.et_msg)

        login_id = intent.getStringExtra("login_id").toString()

        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        lv_chating.adapter = arrayAdapter

        lv_chating.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL)


        str_name= "Guest " + Random().nextInt(1000)


        btn_send.setOnClickListener(View.OnClickListener {
            //map을 사용해 name과 메시지 가져오고 key에 값 요청
            var map : Map<String,Object> = HashMap()

            //key로 데이터베이스 오픈
            val key : String? = reference.push().key
            reference.updateChildren(map)

            var dbRef : DatabaseReference = reference.child(key.toString())

            var objectMap : HashMap<String,String> = HashMap()

            objectMap.put("str_name", str_name)
            objectMap.put("text", et_msg.text.toString())

            dbRef.updateChildren(objectMap as Map<String, Any>)
            et_msg.setText(" ")

        })

        reference.addChildEventListener(object: ChildEventListener{
            override fun onChildRemoved(datasnapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildAdded(datasnapshot: DataSnapshot, previousChildName: String?) {
                chatListener(datasnapshot)
            }

            override fun onChildChanged(datasnapshot: DataSnapshot, previousChildName: String?) {
                chatListener(datasnapshot)
            }

            override fun onChildMoved(datasnapshot: DataSnapshot, previousChildName: String?) {

            }

        })
    }

    private fun chatListener(dataSnapshot: DataSnapshot) {
        val i  = dataSnapshot.getChildren().iterator()

        while (i.hasNext()){
            chat_user = (i.next()).getValue().toString()
            str_msg = (i.next()).getValue().toString()

            arrayAdapter.add(chat_user + " : " + str_msg)
        }

        arrayAdapter.notifyDataSetChanged()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home ->{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                return true
            }
        }
        when(item?.itemId){
            R.id.action_mypage -> {
                val intent = Intent(this,MypageActivity::class.java )
                startActivity(intent)
            }

            //마이페이지로 이동
            R.id.action_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)

                intent.putExtra("user_id", login_id)
                startActivity(intent)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}