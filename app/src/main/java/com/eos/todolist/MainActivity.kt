package com.eos.todolist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eos.todolist.databinding.ActivityMainBinding
import com.eos.todolist.db.AppDatabase
import com.eos.todolist.db.ToDoDao
import com.eos.todolist.db.TodoEntity

class MainActivity : AppCompatActivity(), OnItemLongClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var db: AppDatabase
    private lateinit var todoDao: ToDoDao
    private lateinit var todoList: ArrayList<TodoEntity>

    private lateinit var adapter: TodoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()

    }

    private fun getAllTodoList(){
        Thread{
            todoList = ArrayList(todoDao.getAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView(){
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList, this)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("할 일 삭제")
        builder.setMessage("정말 삭제하시겠습니까?")
        builder.setNegativeButton("아니오", null)
        builder.setPositiveButton("네")
        { p0, p1 -> deleteTodo(position) }
        builder.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTodo(position: Int){
        Thread{
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)

            runOnUiThread {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "삭제되었습니다.",
                    Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

}