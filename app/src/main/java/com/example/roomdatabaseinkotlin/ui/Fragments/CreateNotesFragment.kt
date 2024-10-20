package com.example.roomdatabaseinkotlin.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.roomdatabaseinkotlin.Model.Notes
import com.example.roomdatabaseinkotlin.R
import com.example.roomdatabaseinkotlin.Utility.NotesUtility
import com.example.roomdatabaseinkotlin.ViewModel.NotesViewModel
import com.example.roomdatabaseinkotlin.databinding.FragmentCreateNotesBinding
import java.util.Date

class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    private var priority: String = "1"
    private lateinit var viewModel: NotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNotesBinding.inflate(inflater, container, false)

        binding.greenDot.setImageResource(R.drawable.baseline_done_24)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.greenDot.setOnClickListener{
            priority = "1"
            binding.greenDot.setImageResource(R.drawable.baseline_done_24)
            binding.yellowDot.setImageResource(0)
            binding.redDot.setImageResource(0)
        }
        binding.redDot.setOnClickListener{
            priority = "3"
            binding.redDot.setImageResource(R.drawable.baseline_done_24)
            binding.yellowDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
        }
        binding.yellowDot.setOnClickListener{
            priority = "2"
            binding.yellowDot.setImageResource(R.drawable.baseline_done_24)
            binding.greenDot.setImageResource(0)
            binding.redDot.setImageResource(0)
        }

        binding.saveButton.setOnClickListener {
            addNotes()
        }


        return binding.root
    }

    private fun addNotes() {

        val  d = Date()
        val date = DateFormat.format("MMMM d, yyyy", d.time)

        val notes = Notes(
            null,
            binding.title.text.toString(),
            binding.subTitle.text.toString(),
            binding.notes.text.toString(),
            date.toString(),
            priority,
        )
        viewModel.addNotes(notes)
        NotesUtility.toast(requireContext(), "Notes Created Successfully")
        Navigation.findNavController(binding.root).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }


}