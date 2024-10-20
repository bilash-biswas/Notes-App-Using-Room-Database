package com.example.roomdatabaseinkotlin.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomdatabaseinkotlin.Model.Notes
import com.example.roomdatabaseinkotlin.R
import com.example.roomdatabaseinkotlin.ViewModel.NotesViewModel
import com.example.roomdatabaseinkotlin.databinding.FragmentHomeBinding
import com.example.roomdatabaseinkotlin.ui.Adapter.NotesAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter
    var oldNotes = ArrayList<Notes>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        setHasOptionsMenu(true)

        viewModel.getNotes().observe(viewLifecycleOwner) { noteList ->
            oldNotes = noteList as ArrayList
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = NotesAdapter(requireContext(), noteList)
            binding.recyclerView.adapter = adapter
        }

        binding.addNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { noteList ->
                oldNotes = noteList as ArrayList
                binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = NotesAdapter(requireContext(), noteList)
                binding.recyclerView.adapter = adapter
            }
        }

        binding.highNotes.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { noteList ->
                oldNotes = noteList as ArrayList
                binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = NotesAdapter(requireContext(), noteList)
                binding.recyclerView.adapter = adapter
            }
        }

        binding.mediumNotes.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { noteList ->
                oldNotes = noteList as ArrayList
                binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = NotesAdapter(requireContext(), noteList)
                binding.recyclerView.adapter = adapter
            }
        }

        binding.lowNotes.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { noteList ->
                oldNotes = noteList as ArrayList
                binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = NotesAdapter(requireContext(), noteList)
                binding.recyclerView.adapter = adapter
            }
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Search Notes Here..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNotes(newText)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filterNotes(newText: String?) {
        val newList = ArrayList<Notes>()
        for (i in oldNotes) {
            if (i.title.toLowerCase().contains(newText!!.toLowerCase()) || i.subTitle.toLowerCase().contains(newText!!.toLowerCase()) || i.notes.toLowerCase().contains(
                    newText!!.toLowerCase()
                )
            ) {
                newList.add(i)
            }
        }
        adapter.filtering(newList)
    }
}