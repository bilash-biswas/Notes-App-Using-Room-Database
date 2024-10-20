package com.example.roomdatabaseinkotlin.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseinkotlin.Model.Notes
import com.example.roomdatabaseinkotlin.R
import com.example.roomdatabaseinkotlin.databinding.ItemNotesBinding
import com.example.roomdatabaseinkotlin.ui.Fragments.HomeFragmentDirections

class NotesAdapter(val context : Context, var mList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder> (){

    fun filtering(newFilteredList: ArrayList<Notes>) {
        mList = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val notes = mList[position]
        holder.binding.title.setText(notes.title)
        holder.binding.notes.setText(notes.notes)
        holder.binding.date.setText(notes.date)
        when(notes.priority){
            "1" -> {
                holder.binding.point.setBackgroundResource(R.drawable.green_dot)
            }
            "2" -> {
                holder.binding.point.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3" -> {
                holder.binding.point.setBackgroundResource(R.drawable.red_dot)
            }
        }
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(notes)

            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class MyViewHolder(val binding : ItemNotesBinding) : RecyclerView.ViewHolder(binding.root){

    }
}