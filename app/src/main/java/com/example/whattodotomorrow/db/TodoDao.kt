package com.example.whattodotomorrow.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.whattodotomorrow.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<TodoEntitiy>

    @Query("DELETE  FROM todo ")
    fun deleteAll()

    @Insert
    fun insertTodo( todo: TodoEntitiy)
    @Delete
    fun deleteTodo(todo: TodoEntitiy)

}