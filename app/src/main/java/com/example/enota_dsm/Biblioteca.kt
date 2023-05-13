package com.example.enota_dsm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Biblioteca : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca)
        val directory = context.filesDir // Obtiene el directorio de archivos internos
        val files = directory.listFiles() // Obtiene la lista de archivos en el directorio

        for (file in files) {
            Log.d("FILE", file.name) // Imprime el nombre de cada archivo en la lista en la consola
            // Aquí puedes realizar otras acciones con cada archivo, como mostrarlo en una lista en la actividad
        }

    }
}