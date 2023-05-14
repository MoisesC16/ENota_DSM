package com.example.enota_dsm

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class Biblioteca : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@Biblioteca, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1000)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_biblioteca)
        // Obtén la lista de archivos de audio en el almacenamiento interno
        val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
        val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }


// Crea una lista de strings con los nombres de los archivos de audio
        val audioNames = mutableListOf<String>()
        audioFiles?.forEach { file ->
            audioNames.add(file.name)
        }

// Crea un ArrayAdapter para la lista de nombres de audio
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, audioNames)

// Configura el ListView para utilizar el ArrayAdapter
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter





// Cuando un elemento de la lista se hace clic, reproduce el archivo de audio correspondiente
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFile = audioFiles?.get(position)
            // Aquí puedes utilizar el código para reproducir el archivo de audio
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFile = audioFiles?.get(position)
            selectedFile?.let { file ->
                val audioUri = Uri.parse(file.absolutePath)
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(this, audioUri)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }

    }



}