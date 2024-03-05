package com.example.todolist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    // TODO Obj of Database
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO Database
        todoDatabase = TodoDatabase.getDatabase(this)
//        database = Room.databaseBuilder(
//            applicationContext, TodoDatabase::class.java, "todo_database"
//        ).build()

        // TODO Add Tasks
        add_btn.setOnClickListener {
//            // Return page to CreateCard
            val intent = Intent(this, EnterDetails::class.java)
//            // Activity starts from context (this)
            startActivity(intent)
        }

        // TODO Delete All Tasks
        deleteAll_btn.setOnClickListener {
            DataObject.deleteAll()
            GlobalScope.launch {
                todoDatabase.dao().deleteAllTask()
            }

            // TODO SnackBar
            val snack =
                Snackbar.make(it, "Enter Task", Snackbar.LENGTH_INDEFINITE)
            snack.setAction("ADD") {
                val intent = Intent(this, EnterDetails::class.java)
                startActivity(intent)
                snack.dismiss()
            }
            val snackView = snack.view
            snackView.setBackgroundColor(Color.CYAN)
            val snackText =
                snackView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            snackText.setTextColor(Color.BLACK)
            snack.show()

            setRecycler()
        }
        setRecycler()
    }

    // TODO Set Recycler
    fun setRecycler() {
        // We need to set Adapter in MainActivity
        recycler_view.adapter = Adapter(DataObject.getAllData())
        recycler_view.layoutManager = LinearLayoutManager(this)
    }
}