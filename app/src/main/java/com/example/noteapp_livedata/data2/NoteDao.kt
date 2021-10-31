package com.example.noteapp_room.data2

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp_room.data2.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Notes ORDER BY id ASC")
    fun getNote(): LiveData<List<Note>>

    @Insert
    fun addnote(n: Note)
    @Update
    fun updateNote(note: Note)
    @Delete
    fun deleteNote(note: Note)

}