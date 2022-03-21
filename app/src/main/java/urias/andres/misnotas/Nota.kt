package urias.andres.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File

data class Nota(var title:String, var content:String)

class AdaptadorNotas: BaseAdapter {
    var context: Context
    var notes = ArrayList<Nota>()

    constructor(context: Context, notes:ArrayList<Nota>){
        this.context = context
        this.notes = notes
    }

    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(p0: Int): Any {
        return notes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout,null)
        var note = notes[p0]

        vista.tv_Title_Det.text = note.title
        vista.tv_Contents_Det.text = note.content

        vista.btn_Delete.setOnClickListener {
            delete(note.title)
            notes.remove(note)
            this.notifyDataSetChanged()
        }
        return vista
    }

    private fun delete(title:String){
        if (title ==""){
            Toast.makeText(context, "Error: Empty Title", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archive = File(location(), "$title.txt")
                archive.delete()
                Toast.makeText(context,"The file was deleted", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(context,"Error: File could not be deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun location():String{
        val folder = File(context?.getExternalFilesDir(null),"notas")
        if (!folder.exists()){
            folder.mkdir()
        }
        return folder.absolutePath
    }
}