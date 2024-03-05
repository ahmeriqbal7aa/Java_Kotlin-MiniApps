package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_update_details_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    // TODO Obj of Database
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_details_card)

        // TODO Database
        todoDatabase = TodoDatabase.getDatabase(this)
//        database = Room.databaseBuilder(
//            applicationContext, TodoDatabase::class.java, "todo_database"
//        ).build()

        var posFromAdapter = intent.getIntExtra("id", -1)

        if (posFromAdapter != -1) {

            val title = DataObject.getData(posFromAdapter).title
            val priority = DataObject.getData(posFromAdapter).priority

            // TODO Fields
            update_title.setText(title)
            update_priority.setText(priority)

            // TODO Update
            update_btn.setOnClickListener {
                DataObject.updateData(
                    posFromAdapter,
                    update_title.text.toString(),
                    update_priority.text.toString()
                )
                GlobalScope.launch {
                    todoDatabase.dao().updateTask(
                        Entity(
                            posFromAdapter + 1,
                            update_title.text.toString(),
                            update_priority.text.toString()
                        )
                    )
                }
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                myIntent()
            }

            // TODO Delete
            delete_btn.setOnClickListener {
                DataObject.deleteData(posFromAdapter)
                GlobalScope.launch {
                    todoDatabase.dao().deleteSingleTask(
                        Entity(
                            // "position + 1" mean DB position starts from 1
                            posFromAdapter + 1,
                            update_title.text.toString(),
                            update_priority.text.toString()
                        )
                    )
                }
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show()
                myIntent()
            }
        }
    }

    // TODO Return page to MainActivity
    fun myIntent() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}