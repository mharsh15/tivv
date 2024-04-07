package com.oschmid.tivv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserBookEntity::class], version = 1,exportSchema = false)
abstract class TivwRoomDatabase:RoomDatabase() {

    abstract val userBookDAO:UserBookDAO

    companion object{
        @Volatile
        private var INSTANCE:TivwRoomDatabase?=null
        fun getInstance(context: Context):TivwRoomDatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,TivwRoomDatabase::class.java,"all_news_db")
                        .fallbackToDestructiveMigration()
                        .build()

                }
                return instance
            }
        }

    }

}