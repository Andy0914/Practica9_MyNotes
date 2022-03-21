package urias.andres.misnotas

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_agregar_nota.*
import java.io.File
import java.io.FileOutputStream

class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)
        btn_Save.setOnClickListener {
            saveNotes()
        }

    }

    private fun saveNotes(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 235)
        }
        else{
            save()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            235 ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    save()
                }else{
                    Toast.makeText(this,"Error: Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun save(){
        var title = et_Title.text.toString()
        var content = et_Content.text.toString()
        if(title == "" || content ==""){
            Toast.makeText(this,"Error: Empty fields", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val file = File(location(), title+".txt")
                val fos = FileOutputStream(file)
                fos.write(content.toByteArray())
                fos.close()
                Toast.makeText(this,"The file was saved to the public folder", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this,"Error:The file was NOT saved", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun location():String{
        val file = File(getExternalFilesDir(null),"notas")
        if (!file.exists()){
            file.mkdir()
        }
        return file.absolutePath
    }
}