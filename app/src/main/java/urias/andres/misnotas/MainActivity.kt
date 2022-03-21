package urias.andres.misnotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    var notes = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            var intent = Intent(this,AgregarNotaActivity::class.java)
            startActivityForResult(intent,123)
        }
        readNotes()
        adaptador = AdaptadorNotas(this,notes)
        listView.adapter = adaptador
    }
    private fun readNotes(){
        notes.clear()
        var file = File(location())

        if (file.exists()){
            var archives = file.listFiles()
            if (archives!= null){
                for (archive in archives){
                    readFile(archive)
                }
            }
        }
    }

    private fun readFile(file: File?) {
        val fileInput = FileInputStream(file)
        val dataInput = DataInputStream(fileInput)
        val bufferRead = BufferedReader(InputStreamReader(dataInput))
        var strLine: String? = bufferRead.readLine()
        var myData =""

        while (strLine != null){
            myData += strLine
            strLine = bufferRead.readLine()
        }
        bufferRead.close()
        dataInput.close()
        fileInput.close()
        var name = file?.name?.substring(0,file.name.length-4)
        var note = name?.let { Nota(it,myData) }
        note?.let { notes.add(it) }
    }

    private fun location():String{
        val folder = File(getExternalFilesDir(null),"notas")
        if (!folder.exists()){
            folder.mkdir()
        }
        return folder.absolutePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            readNotes()
            adaptador.notifyDataSetChanged()
        }
    }
}