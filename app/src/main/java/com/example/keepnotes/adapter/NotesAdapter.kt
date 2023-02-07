package com.example.keepnotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.Notes
import com.example.keepnotes.R
import com.example.keepnotes.databinding.ItemNotesBinding
import com.example.keepnotes.fragment.HomeFragmentDirections

class NotesAdapter(val context: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    fun searchedNotes(newSearchList: ArrayList<Notes>) {
        notesList = newSearchList
        notifyDataSetChanged()
    }

    class NotesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val list = notesList[position]
        holder.binding.notesTitle.text = list.title
        holder.binding.notes.text = list.notes
        holder.binding.notesDate.text = list.date

        when (list.priority) {
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }
            "2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
        }
        holder.binding.notes.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(list)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(list)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}