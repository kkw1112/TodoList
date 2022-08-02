package com.eos.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eos.todolist.databinding.ActivityAddTodoBinding
import com.eos.todolist.databinding.ActivityMainBinding
import com.eos.todolist.db.AppDatabase
import com.eos.todolist.db.ToDoDao
import com.eos.todolist.db.TodoEntity

class AddTodoActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddTodoBinding

    private lateinit var db: AppDatabase
    private lateinit var todoDao: ToDoDao
    private lateinit var todoList: ArrayList<TodoEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle: String = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId

        when (todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }

            R.id.btn_middle -> {
                todoImportance = 2
            }

            R.id.btn_low -> {
                todoImportance =3
            }

            else -> {
                todoImportance = -1
            }
        }

        if (todoImportance == -1 || todoTitle.isBlank()){
            Toast.makeText(this, "모든 항목을 입력해주세요.",
                Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                todoDao.insertTodo(TodoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(this, "추가되었습니다.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}
