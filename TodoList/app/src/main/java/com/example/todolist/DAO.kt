package com.example.todolist

import androidx.room.*

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Update
     suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteSingleTask(entity: Entity)

    @Query("Delete from todo_table")
    suspend fun deleteAllTask()

    @Query("Select * from todo_table")
    suspend fun getAllTask():List<CardInfo>

}