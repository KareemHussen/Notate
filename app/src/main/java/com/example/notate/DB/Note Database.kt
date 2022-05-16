package com.example.notate.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteModel:: class] , version = 4 , exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {


    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: NoteDatabase? = null

        private val LOCK = Any()

        fun instanceOfDatabase(context: Context): NoteDatabase {
            synchronized(LOCK) {

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "NotesDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()


                }
                return instance!!
            }

        }



    }

}

