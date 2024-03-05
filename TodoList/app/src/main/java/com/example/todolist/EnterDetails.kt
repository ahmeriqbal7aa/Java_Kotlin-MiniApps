package com.example.todolist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_enter_details_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EnterDetails : AppCompatActivity() {
    // TODO Obj of Database
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_details_card)

        // TODO Database
        todoDatabase = TodoDatabase.getDatabase(this)
//        database = Room.databaseBuilder(
//            applicationContext, TodoDatabase::class.java, "todo_database"
//        ).build()

        // TODO Enter Details
        save_btn.setOnClickListener {
            if (enter_title.text.toString().trim { it <= ' ' }.isNotEmpty()
                && enter_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {

                var title = enter_title.text.toString()
                var priority = enter_priority.text.toString()

                // TODO Set Data
                DataObject.setData(title, priority)

                // TODO Set Data to Database
                //  GlobalScope will Create Coroutine
                GlobalScope.launch {
                    todoDatabase.dao().insertTask(Entity(0, title, priority))
                }

                // View Data in "Logcat"
//                GlobalScope.launch {
//                    Log.i("Ahmer",database.dao().getTask().toString())
//                }

                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()

                // Return page to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                // Activity starts from context (this)
                startActivity(intent)
            }
        }
    }
}