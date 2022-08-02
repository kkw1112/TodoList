package com.eos.todolist;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;

import com.eos.todolist.databinding.ItemTodoBinding;
import com.eos.todolist.db.TodoEntity;

import java.util.ArrayList;

class TodoRecyclerViewAdapter(private val todoList:ArrayList<TodoEntity>)
    :RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>(){
    inner

    class MyViewHolder(binding:ItemTodoBinding) :RecyclerView.ViewHolder(binding.root){
        val tv_importance=binding.tvImportance
        val tv_title=binding.tvTitle

        val root=binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemTodoBinding
        = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when (todoData.importance){
            1-> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2-> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3->{
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }

        holder.tv_importance.text = todoData.title
        holder.tv_title.text = todoData.title
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}
