package com.example.keepnotes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.keepnotes.Notes
import com.example.keepnotes.R
import com.example.keepnotes.database.NotesDatabase
import com.example.keepnotes.databinding.FragmentCreateNotesBinding
import com.example.keepnotes.repository.NotesRepository
import com.example.keepnotes.viewmodal.NotesViewModal
import com.example.keepnotes.viewmodal.NotesViewModalFactory
import java.util.*

class CreateNotesFragment : Fragment() {

    private lateinit var binding: FragmentCreateNotesBinding
    lateinit var repository: NotesRepository
    lateinit var viewModal: NotesViewModal
    var priority = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        val dao = NotesDatabase.getDatabase(requireContext()).getNotesDao()
        repository = NotesRepository(dao)

        viewModal = ViewModelProvider(
            this,
            NotesViewModalFactory(repository)
        )[NotesViewModal::class.java]

        binding.pGreen.setImageResource(R.drawable.ic_check)
        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_check)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_check)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }
        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_check)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }
        return binding.root
    }
    private fun createNotes(it: View?) {
        val title = binding.etTitle.text.toString()
        val notes = binding.etNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = android.text.format.DateFormat.format("MMMM d,yyyy", d.time)

        val data = Notes(null, title, notes, priority, notesDate.toString())

        if (title == "") {
            Toast.makeText(requireContext(), "Please Enter Title", Toast.LENGTH_SHORT).show()
        } else {
            if (notes == "") {
                Toast.makeText(requireContext(), "Please Enter Notes", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModal.insertNotes(data)
                Toast.makeText(
                    requireContext(),
                    "Notes Created successfully",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(it!!)
                    .navigate(R.id.action_createNotesFragment_to_homeFragment)
            }
        }
    }
}