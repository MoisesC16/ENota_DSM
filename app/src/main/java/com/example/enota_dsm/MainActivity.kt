package com.example.enota_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGrabadora = findViewById<Button>(R.id.btnGrabadora)
        btnGrabadora.setOnClickListener {
            val intent = Intent(this, Grabadora::class.java)
            startActivity(intent)
        }
        val btnBiblioteca = findViewById<Button>(R.id.btnBiblioteca)
        btnBiblioteca.setOnClickListener {
            val intent = Intent(this, Biblioteca::class.java)
            startActivity(intent)
        }
    }
}