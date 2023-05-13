package com.example.enota_dsm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment.getExternalStorageDirectory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException
import java.util.*

class Grabadora : AppCompatActivity() {

    var grabadora:MediaRecorder?=null
    var ruta:String?=null
    var imgGrabar:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grabadora)

        val btnRegresarM1 = findViewById<Button>(R.id.btnRegresarM1)
        btnRegresarM1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imgGrabar = findViewById(R.id.imgGrabar)

        //Permisos a preguntar al usuario
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@Grabadora, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1000)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun grabar(view : View){
        val dateFormat = SimpleDateFormat("MM_dd_HH_mm_ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val fileName = "Audio_$currentDate.mp3"
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        val outputFile = File(dir, fileName)
        if(grabadora==null) {

            grabadora = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(outputFile)
            }
            try {

                grabadora?.prepare()
                grabadora?.start()
                imgGrabar?.setBackgroundColor(Color.RED)
                Toast.makeText(applicationContext, "Grabando...", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                println(e)
            }
        }else{

            try {
                grabadora?.stop()
              //  grabadora?.reset()
                grabadora?.release()



                imgGrabar?.setBackgroundColor(Color.BLACK)

                Toast.makeText(applicationContext, "Grabación Terminada", Toast.LENGTH_SHORT).show()
            }catch (e:IOException){
                println(e)
            }
        }
    }

    private fun UbicacionArchivo(): String {
        if(ruta == null) {
            val folder = File(getExternalStorageDirectory(), "ENota_DSM")
            if(!folder.exists()) {
                folder.mkdirs()
            }
            ruta = "${folder.absolutePath}/${System.currentTimeMillis()}.mp3"
        }
        return ruta as String
    }
    fun reproducir(view : View){
        var mediaPlayer = MediaPlayer()
        try{
            mediaPlayer.setDataSource(ruta)
            mediaPlayer.prepare()
        }catch (e:IOException){
            println(e)
        }
        mediaPlayer.start()
        Toast.makeText(applicationContext, "Reproduciendo Audio", Toast.LENGTH_SHORT).show()
    }
}