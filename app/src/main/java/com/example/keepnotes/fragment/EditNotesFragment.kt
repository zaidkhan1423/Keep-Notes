package com.example.keepnotes.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.keepnotes.Notes
import com.example.keepnotes.R
import com.example.keepnotes.activity.MainActivity
import com.example.keepnotes.database.NotesDatabase
import com.example.keepnotes.databinding.FragmentEditNotesBinding
import com.example.keepnotes.repository.NotesRepository
import com.example.keepnotes.viewmodal.NotesViewModal
import com.example.keepnotes.viewmodal.NotesViewModalFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {

    lateinit var binding: FragmentEditNotesBinding
    private val oldNotes by navArgs<EditNotesFragmentArgs>()
    var priority = "1"
    lateinit var viewModal: NotesViewModal
    lateinit var repository: NotesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        val dao = NotesDatabase.getDatabase(requireContext()).getNotesDao()
        repository = NotesRepository(dao)
        viewModal =
            ViewModelProvider(
                this, NotesViewModalFactory(repository)
            )[NotesViewModal::class.java]

        binding.etNotes.setText(oldNotes.list.notes)
        binding.etTitle.setText(oldNotes.list.title)

        when (oldNotes.list.priority) {
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_check)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_check)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_check)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
        }
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

        binding.btnUpdateNotes.setOnClickListener {
            updateNotes(it)
        }
        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.etTitle.text.toString()
        val notes = binding.etNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = android.text.format.DateFormat.format("MMMM d,yyyy", d.time)

        val data = Notes(oldNotes.list.id, title, notes, priority, notesDate.toString())

        if (title == "") {
            Toast.makeText(requireContext(), "Please Enter Title", Toast.LENGTH_SHORT).show()
        } else {
            if (notes == "") {
                Toast.makeText(requireContext(), "Please Enter Notes", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModal.updateNotes(data)
                Toast.makeText(
                    requireContext(),
                    "Notes Updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(it!!)
                    .navigate(R.id.action_editNotesFragment_to_homeFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.deleteMenu) {
            val bottomSheet = BottomSheetDialog(requireContext(),R.style.bottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()

            val textYes = bottomSheet.findViewById<TextView>(R.id.dialogYes)
            val textNo = bottomSheet.findViewById<TextView>(R.id.dialogNo)

            textYes?.setOnClickListener {
                viewModal.deleteNotes(oldNotes.list.id!!)
                Toast.makeText(context,"Notes Delete Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
            }
            textNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}