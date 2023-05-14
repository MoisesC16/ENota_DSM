package com.example.enota_dsm

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.view.ContextMenu
import android.view.View
import android.widget.*
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


        registerForContextMenu(listView)


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

   override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
        val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }
        
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val audio = audioFiles[info.position]
        menu?.findItem(R.id.share_item)?.setOnMenuItemClickListener {
            shareAudio(audio)
            true
        }
       menu?.findItem(R.id.edit_item)?.setOnMenuItemClickListener {
           editName(audio)
           true
       }
       menu?.findItem(R.id.delete_item)?.setOnMenuItemClickListener {
           delete(audio)
           true
       }
    }
    fun shareAudio(audio: File) {
        val uri = Uri.parse(audio.absolutePath)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "audio/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "Share Audio"))
    }
fun editName(audio: File){
    val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
    val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }
    val audioNames = mutableListOf<String>()
    audioFiles?.forEach { file ->
        audioNames.add(file.name)
    }

    val builder = AlertDialog.Builder(this)
    builder.setTitle("Editar nombre")

    val input = EditText(this)
    input.setText("")
    builder.setView(input)

    builder.setPositiveButton("Aceptar") { _, _ ->
       val newAudioName = input.text.toString()
        val newFileName = "$newAudioName.mp3"
        val newFile = File(audio.parent, newFileName)
        if (audio.renameTo(newFile)) {
            Toast.makeText(applicationContext, "Renombrado con éxito $newFile", Toast.LENGTH_SHORT).show()
            // El archivo se ha renombrado con éxito
            updateAudioList()
        } else {
            Toast.makeText(applicationContext, "Error$newFile", Toast.LENGTH_SHORT).show()
            // No se pudo renombrar el archivo
        }

        // Acciones a realizar al aceptar el nuevo nombre del audio
    }

    builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

    val dialog = builder.create()
    dialog.show()


}
    fun delete(audio: File){
        val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
        val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }


        if (audio.delete()) {
            Toast.makeText(applicationContext, "Eliminado con exito", Toast.LENGTH_SHORT).show()
            updateAudioList()
        } else {
            // No se pudo eliminar el archivo
        }
    }

    private fun updateAudioList() {


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


        registerForContextMenu(listView)
        // Actualizar la lista de audios en el adaptador


        // Notificar al adaptador de que los datos han cambiado

    }

    /* override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
         super.onCreateContextMenu(menu, v, menuInfo)
         val inflater = menuInflater
         inflater.inflate(R.menu.menu, menu)
     }*/


   /* private fun showRenameDialog(selectedFile: File) {
        val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
        val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }
        val audioNames = mutableListOf<String>()
        audioFiles?.forEach { file ->
            audioNames.add(file.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, audioNames)

        input.setText(selectedFile.nameWithoutExtension)

        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_rename)
            .setView(view)
            .setPositiveButton(R.string.dialog_button_rename) { dialog, _ ->
                val newName = input.text.toString()
                val newFile = File(selectedFile.parent, "$newName.${selectedFile.extension}")
                selectedFile.renameTo(newFile)
                updateAudioList()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_button_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }
    private fun updateAudioList() {
        val audioDir = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "")
        val audioFiles = audioDir.listFiles { file -> file.extension == "mp3" }
        val audioNames = mutableListOf<String>()
        audioFiles?.forEach { file ->
            audioNames.add(file.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, audioNames)
       audioNames.clear()
        audioNames.addAll(getAudioFiles())
        adapter.notifyDataSetChanged()
    }*/


}