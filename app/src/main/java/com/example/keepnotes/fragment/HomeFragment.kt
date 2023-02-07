package com.example.keepnotes.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.keepnotes.Notes
import com.example.keepnotes.R
import com.example.keepnotes.adapter.NotesAdapter
import com.example.keepnotes.database.NotesDatabase
import com.example.keepnotes.databinding.FragmentHomeBinding
import com.example.keepnotes.repository.NotesRepository
import com.example.keepnotes.viewmodal.NotesViewModal
import com.example.keepnotes.viewmodal.NotesViewModalFactory
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModal: NotesViewModal
    lateinit var repository: NotesRepository
    lateinit var notesAdapter: NotesAdapter
    var myNotes = arrayListOf<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        val dao = NotesDatabase.getDatabase(requireContext()).getNotesDao()

        repository = NotesRepository(dao)

        viewModal = ViewModelProvider(this,NotesViewModalFactory(repository))[NotesViewModal::class.java]

        viewModal.getAllNotes().observe(viewLifecycleOwner, Observer {
            myNotes = it as ArrayList<Notes>
            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
            notesAdapter = NotesAdapter(activity as Context, it)
            binding.rcvAllNotes.adapter = notesAdapter
        })

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.btnAllNotes.setBackgroundResource(R.drawable.bg_dark_gray)
        //For get all Notes
        binding.btnAllNotes.setOnClickListener{
            binding.btnAllNotes.setBackgroundResource(R.drawable.bg_dark_gray)
            binding.btnFilterLow.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterHigh.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterMedium.setBackgroundResource(R.drawable.bg_high_filter)
            viewModal.getAllNotes().observe(viewLifecycleOwner, Observer {
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                notesAdapter = NotesAdapter(activity as Context, it)
                binding.rcvAllNotes.adapter = notesAdapter
            })
        }
        //For get yellow color priority notes
        binding.btnFilterLow.setOnClickListener {

            binding.btnFilterLow.setBackgroundResource(R.drawable.bg_dark_gray)
            binding.btnAllNotes.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterHigh.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterMedium.setBackgroundResource(R.drawable.bg_high_filter)

            viewModal.getLowNotes().observe(viewLifecycleOwner, Observer {
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                notesAdapter = NotesAdapter(activity as Context, it)
                binding.rcvAllNotes.adapter = notesAdapter
            })
        }
        //For get green color priority Notes
        binding.btnFilterMedium.setOnClickListener {

            binding.btnFilterMedium.setBackgroundResource(R.drawable.bg_dark_gray)
            binding.btnAllNotes.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterHigh.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterLow.setBackgroundResource(R.drawable.bg_high_filter)

            viewModal.getMediumNotes().observe(viewLifecycleOwner, Observer {
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                notesAdapter = NotesAdapter(activity as Context, it)
                binding.rcvAllNotes.adapter = notesAdapter
            })
        }
        //For get red color priority notes
        binding.btnFilterHigh.setOnClickListener {

            binding.btnFilterHigh.setBackgroundResource(R.drawable.bg_dark_gray)
            binding.btnAllNotes.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterLow.setBackgroundResource(R.drawable.bg_high_filter)
            binding.btnFilterMedium.setBackgroundResource(R.drawable.bg_high_filter)

            viewModal.getHighNotes().observe(viewLifecycleOwner, Observer {
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                notesAdapter = NotesAdapter(activity as Context, it)
                binding.rcvAllNotes.adapter = notesAdapter
            })
        }

        //for not going to previous fragment and want to exit app on back press
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
        //
        return binding.root
    }
    // for add search bar on action bar and its working
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
               return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                onSearch(p0)
              return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun onSearch(p0: String?) {
        val newSearchList = arrayListOf<Notes>()
        for (i in myNotes){
            if (i.title.toUpperCase(Locale.ROOT).contains(p0!!.toUpperCase(Locale.ROOT)) || i.title.toLowerCase(
                    Locale.ROOT
                ).contains(p0?.toLowerCase(Locale.ROOT))
            ) {
                newSearchList.add(i)
            }
        }
        notesAdapter.searchedNotes(newSearchList)
    }
}