package com.example.paintschemetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//This is the database, the miniature entity is passed in
@Database(entities = [MiniatureEntity::class], version = 3, exportSchema = false)
//This is a room database which is an sqlite library
abstract class AppDatabase: RoomDatabase() {
//the miniature dao is called here
    abstract fun miniatureDao(): MiniatureDao?
//this allows the database to be nullable
    companion object{
        private var INSTANCE: AppDatabase? = null
//the get instance starts the instance of the database, and synchronizes it with room
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "paintschemetracker.db"
                    )
                            //this builds and allows your database instance to start even if the version number is changed
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
    //the instance is then returned
            return INSTANCE
        }
    }
}