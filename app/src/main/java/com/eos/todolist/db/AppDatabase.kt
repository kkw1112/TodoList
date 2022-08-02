package com.eos.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoDao() : ToDoDao

    companion object{
        val todoTitle: String = "db_todo"
        var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    todoTitle).build()
            }

            return appDatabase
        }
    }
}