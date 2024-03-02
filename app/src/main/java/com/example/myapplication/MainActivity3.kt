package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView


class MainActivity3 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var button:Button = findViewById(R.id.button3)
        var textBox : EditText = findViewById(R.id.editTextText)
        var listvd:RecyclerView = findViewById(R.id.recyclerView)


        button.setOnClickListener{

        }
    }
}