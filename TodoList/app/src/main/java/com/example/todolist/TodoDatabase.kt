package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// version mean this is "First DB".
// Helpful when we do Migration
@Database(entities = [Entity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun dao(): DAO

    // TODO Create a Singleton
    companion object {
        // if any value assigned to "instance"
        // "Volatile" annotation tell all threads that instance's value is updated
        // and updated value assigned to all threads
        @Volatile
        // private field which holds the DB
        private var instance: TodoDatabase? = null

        // public function which help us to access DB from private field
        fun getDatabase(context: Context): TodoDatabase {
            if (instance == null) {
                // we make multiple threads in android for multiple operations
                // so there is a possibility that two threads try to create this obj simultaneously
                // through this multiple DB instances will be created
                // to avoid this we use locking concept using "synchronized block"
                // which will help to create only single instance of database
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java, "todo_database"
                    ).build()
                }
            }
            return instance!!
        }
    }
}