package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    // TODO Obj of Database
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // TODO Database
        todoDatabase = TodoDatabase.getDatabase(this)
//        database = Room.databaseBuilder(
//            applicationContext, TodoDatabase::class.java, "todo_database"
//        ).build()

        // When we open app after close, data lost, listData empty
        // So where would data comes from, for this we get task
        // Fetch data which is set/stored in database
        GlobalScope.launch {
            DataObject.listData = todoDatabase.dao().getAllTask() as MutableList<CardInfo>
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // Return page to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Activity starts from context (this)
            startActivity(intent)
        }, 2000)
    }
}