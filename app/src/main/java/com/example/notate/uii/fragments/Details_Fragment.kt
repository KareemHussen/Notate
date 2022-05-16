package com.example.notate.uii.fragments

import android.app.ProgressDialog.show
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notate.viewModel.NoteViewModel
import com.example.notate.DB.NoteModel
import com.example.notate.R
import com.example.notate.repository.Repository
import com.example.notate.viewModel.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detailes.*
import java.text.SimpleDateFormat
import java.util.*

class Details_Fragment : Fragment(R.layout.fragment_detailes) {

    lateinit var viewModel: NoteViewModel

    private val args by navArgs<Details_FragmentArgs>()
    // private val arg : Details_FragmentArgs by navArgs() // both right

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var argss = this.arguments


        var note = args.note

        if (note == null){
            note = NoteModel(argss!!.getString("note")!! , "gamed" , false , false , ":2002" , false)

        }

        if (note.isPinned) {
            pinButton.setBackgroundResource(R.drawable.icons8_pin_64__1_)
        } else {
            pinButton.setBackgroundResource(R.drawable.ic_outline_push_pin_24)

        }

        noteTitle.setText(note.title)
        noteContent.setText(note.content)
        lastEditied.text = note.lastEdited

        this.requireActivity().drawer_layout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)

        val repository = Repository()
        val viewModelFactor = NoteViewModelFactory(repository , requireContext())
        viewModel = ViewModelProvider(this , viewModelFactor).get(NoteViewModel::class.java)

        noteTitle.addTextChangedListener {
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            lastEditied.text = currentDate
        }

        noteContent.addTextChangedListener {
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            lastEditied.text = currentDate
        }

        ar_MenuIcon.setOnClickListener {
            note.title = noteTitle.text.toString()
            note.content = noteContent.text.toString()
            note.lastEdited = lastEditied.text.toString()

            if (note.update) {
                viewModel.updateNote(note)
            } else {
                viewModel.insertNote(note)
            }

            val action = Details_FragmentDirections.actionDetailesFragmentToNotesFragment()
            findNavController().navigate(action)

        }




        archiveButton.setOnClickListener {

            if (note.isArchived){

                note.isArchived = !note.isArchived

                viewModel.updateNote(note)

                Snackbar.make(view , "Note Has Been Deleted From Archive" , Snackbar.LENGTH_LONG).show()

                val action = Details_FragmentDirections.actionDetailesFragmentToNotesFragment()
                findNavController().navigate(action)


            } else {

                note.isArchived = !note.isArchived

                viewModel.updateNote(note)

                Snackbar.make(view , "Note Has Been Archived" , Snackbar.LENGTH_LONG).show()

                val action = Details_FragmentDirections.actionDetailesFragmentToNotesFragment()
                findNavController().navigate(action)
            }

        }

        pinButton.setOnClickListener {
            if (note.isPinned) {
                note.isPinned = false
                pinButton.setBackgroundResource(R.drawable.ic_outline_push_pin_24)

                Toast.makeText(requireContext(), "Note is Not Pinned", Toast.LENGTH_SHORT).show()
            } else {
                note.isPinned = true
                pinButton.setBackgroundResource(R.drawable.icons8_pin_64__1_)

                Toast.makeText(requireContext(), "Note is Pinned", Toast.LENGTH_SHORT).show()

            }

                viewModel.updateNote(note)



        }



        deleteButton.setOnClickListener {

            viewModel.deleteNote(note)
            val action = Details_FragmentDirections.actionDetailesFragmentToNotesFragment()
            findNavController().navigate(action)

            Snackbar.make(view , "Note Has Been Deleted" , Snackbar.LENGTH_LONG).setAction("Undo") {
                viewModel.insertNote(note)
                val action = Notes_FragmentDirections.actionNotesFragmentToDetailesFragment(note)
                findNavController().navigate(action)
            }.setActionTextColor(Color.WHITE)
                .show()





        }


        reminderButton.setOnClickListener {

            val bottomSheetDialog = BottomSheet()
            bottomSheetDialog.show(parentFragmentManager , "Gamed")

        }



    }





}