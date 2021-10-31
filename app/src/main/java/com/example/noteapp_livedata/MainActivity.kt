package com.example.noteapp_livedata

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp_room.data2.Note
import com.example.noteapp_room.data2.NoteDatabase

class MainActivity : AppCompatActivity() {

    lateinit var save: Button
    lateinit var note: EditText
    lateinit var recycle: RecyclerView


    lateinit var myViewModel :MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        note=findViewById(R.id.ednote)
        save=findViewById(R.id.btnsave)
        recycle=findViewById(R.id.rv)

        myViewModel=ViewModelProvider(this).get(MyViewModel::class.java)

        updatedrecycle()


        save.setOnClickListener {
            val s1=note.text.toString()
            if(s1.isNotEmpty()) {
                myViewModel.addNotes(s1)
                note.text.clear()

                Toast.makeText(applicationContext, "data successfully added", Toast.LENGTH_SHORT)
                    .show()



            }
            else{
                Toast.makeText(applicationContext,"please add note first", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun updatedrecycle(){
        myViewModel.getnotes().observe(this,{
                notes1->
        recycle.adapter = RVAdapter (this, notes1)
        recycle.layoutManager = LinearLayoutManager(this)
        })

    }
    fun update(id:Int,n:Note) {

        var at= AlertDialog.Builder(this)
        at.setTitle("Edit Note")
        val input = EditText(this)
        input.setHint(n.note)
        at.setView(input)

        at.setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->
            if(input.text.isNotEmpty()) {

               myViewModel.updatesNotes(id, input.text.toString())
                Toast.makeText(applicationContext, "data successfully Edited", Toast.LENGTH_SHORT)
                    .show()
            }else{

                Toast.makeText(applicationContext, "Field is empty", Toast.LENGTH_SHORT)
                    .show()
            }


        })

        at.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alt1: AlertDialog = at.create()
        alt1.setCanceledOnTouchOutside(false)

        alt1.show()
    }

    fun confirm(id:Int){

        var at= AlertDialog.Builder(this)
        at.setTitle("delete Note")
        at.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
            deleteitem(id)
        })
        at.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        at.show()
    }

    private fun deleteitem(id:Int) {
        val ob= NoteDatabase.getinstant(applicationContext)
        myViewModel.deleteNotes(id)
        Toast.makeText(applicationContext, "data successfully Deleted", Toast.LENGTH_SHORT)
            .show()

    }

}