package com.example.crudpeminjamanbuku_agi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Buku::class],
    version = 1
)
abstract class BukuDb : RoomDatabase(){
    abstract fun bukuDao() : BukuDao

    companion object {
        @Volatile private  var instance : BukuDb? = null
        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BukuDb::class.java,
            "buku12345.db"
        ).build()
    }
}