package com.example.roomdatabaseinkotlin.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabaseinkotlin.Model.Notes
import com.example.roomdatabaseinkotlin.R
import com.example.roomdatabaseinkotlin.Utility.NotesUtility
import com.example.roomdatabaseinkotlin.ViewModel.NotesViewModel
import com.example.roomdatabaseinkotlin.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditNotesFragment : Fragment() {
    private lateinit var binding: FragmentEditNotesBinding
    private lateinit var viewModel: NotesViewModel
    val notes by navArgs<EditNotesFragmentArgs>()
    var priority: String = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        })

        binding.title.setText(notes.notes.title)
        binding.subTitle.setText(notes.notes.subTitle)
        binding.notes.setText(notes.notes.notes)
        when (notes.notes.priority) {
            "1" -> {
                priority = "1"
                binding.greenDot.setImageResource(R.drawable.baseline_done_24)
                binding.yellowDot.setImageResource(0)
                binding.redDot.setImageResource(0)
            }

            "3" -> {
                priority = "3"
                binding.redDot.setImageResource(R.drawable.baseline_done_24)
                binding.yellowDot.setImageResource(0)
                binding.greenDot.setImageResource(0)
            }

            "2" -> {
                priority = "2"
                binding.yellowDot.setImageResource(R.drawable.baseline_done_24)
                binding.greenDot.setImageResource(0)
                binding.redDot.setImageResource(0)
            }
        }
        binding.greenDot.setOnClickListener {
            priority = "1"
            binding.greenDot.setImageResource(R.drawable.baseline_done_24)
            binding.yellowDot.setImageResource(0)
            binding.redDot.setImageResource(0)
        }
        binding.redDot.setOnClickListener {
            priority = "3"
            binding.redDot.setImageResource(R.drawable.baseline_done_24)
            binding.yellowDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
        }
        binding.yellowDot.setOnClickListener {
            priority = "2"
            binding.yellowDot.setImageResource(R.drawable.baseline_done_24)
            binding.greenDot.setImageResource(0)
            binding.redDot.setImageResource(0)
        }

        binding.saveButton.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(view: View) {
        val updateNotes = Notes(
            notes.notes.id,
            binding.title.text.toString(),
            binding.subTitle.text.toString(),
            binding.notes.text.toString(),
            notes.notes.date,
            priority
        )
        viewModel.updateNotes(updateNotes)
        NotesUtility.toast(requireContext(), "Notes Updated Successfully")
        view.findNavController().navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteButton) {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheet)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val yes = bottomSheet.findViewById<View>(R.id.yesButton)
            val no = bottomSheet.findViewById<View>(R.id.noButton)

            yes?.setOnClickListener {
                viewModel.deleteNotes(notes.notes.id!!)
                NotesUtility.toast(requireContext(), "Notes Deleted Successfully")
                bottomSheet.dismiss()
                view?.findNavController()?.navigate(R.id.action_editNotesFragment_to_homeFragment)
            }
            no?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()

        }
        return super.onOptionsItemSelected(item)
    }
}