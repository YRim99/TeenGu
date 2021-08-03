package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class PInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinfo)

        setTitle("개인정보처리방침")

        val path = "/data/data/com.example.login/files/1.txt"
        var bufferReader : BufferedReader = File(path).bufferedReader()
        var inputString = bufferReader.use { it.readText() }

        var first_beforechange = findViewById(R.id.first) as TextView?
        first_beforechange?.setText(inputString)

        val path2 = "/data/data/com.example.login/files/2.txt"
        bufferReader = File(path2).bufferedReader()
        inputString = bufferReader.use { it.readText() }

        var second_beforechange = findViewById(R.id.second) as TextView?
        second_beforechange?.setText(inputString)

        val path3 = "/data/data/com.example.login/files/3.txt"
        bufferReader = File(path3).bufferedReader()
        inputString = bufferReader.use { it.readText() }

        var third_beforechange = findViewById(R.id.third) as TextView?
        third_beforechange?.setText(inputString)

    }
}


