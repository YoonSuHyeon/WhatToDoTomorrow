package com.example.whattodotomorrow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodotomorrow.databinding.TodoItemBinding

class TodoAdapter :RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    var list : List<Todo> = ArrayList()

    inner class TodoViewHolder(var binding : TodoItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(todo:Todo){
            binding.setVariable(BR.todo,todo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
       val binding  = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(binding)
    }
    fun setItem(board: List<Todo>){
        this.list=board
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       val todo = list[position]
        holder.bind(todo)
    }
}