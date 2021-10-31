package com.example.noteapp_livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteapp_room.data2.Note
import com.example.noteapp_room.data2.NoteDatabase
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyViewModel (activity: Application): AndroidViewModel(activity){
    private val notes:LiveData<List<Note>>
   // private val repository:Repository
    val ob= NoteDatabase.getinstant(activity)

    init {

        notes=ob.NoteDao().getNote()





    }

    fun getnotes(): LiveData<List<Note>>{
        return notes
    }
    fun addNotes(n: String) {

        GlobalScope.launch(Main)
        {
  ob.NoteDao().addnote(Note(0,n))

            }
        }
    fun updatesNotes(id:Int,n: String) {
        GlobalScope.launch(Main)
        {
            ob.NoteDao().updateNote(Note(id, n))

        }
    }

    fun deleteNotes(id:Int) {
        GlobalScope.launch(Main)
        {
            ob.NoteDao().deleteNote(Note(id))

        }
    }
    }

