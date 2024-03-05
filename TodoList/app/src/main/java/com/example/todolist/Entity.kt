package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: String
)