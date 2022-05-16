package com.example.notate.uii.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notate.viewModel.NoteViewModel
import com.example.notate.DB.NoteModel
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*
import NoteAdapter
import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notate.R
import com.example.notate.repository.Repository
import com.example.notate.viewModel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notes.rvNotes
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Notes_Fragment : Fragment(R.layout.fragment_notes) {

    val noteAdapter: NoteAdapter by lazy {
        NoteAdapter()
    }

    lateinit var viewModel: NoteViewModel


    @SuppressLint("WrongConstant")
    override fun onResume() {
        super.onResume()




        setupRecyclerView()
        rvNotes.adapter = noteAdapter

        val repository = Repository()
        val viewModelFactor = NoteViewModelFactory(repository , requireContext())
        viewModel = ViewModelProvider(this , viewModelFactor).get(NoteViewModel::class.java)

        viewModel.getNotes()

        viewModel.notesMutable.observe(requireActivity(), androidx.lifecycle.Observer {
            noteAdapter.submitList(it)
        })

        noteAdapter.setOnItemClickListner {


            it.update = true
            val action = Notes_FragmentDirections.actionNotesFragmentToDetailesFragment(it)
            findNavController().navigate(action)

        }

        fab.setOnClickListener {
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val note = NoteModel("", "", false, false, currentDate, false)
            val action = Notes_FragmentDirections.actionNotesFragmentToDetailesFragment(note)
            findNavController().navigate(action)
        }

        menuIcon.setOnClickListener {
            this.activity!!.drawer_layout.openDrawer(Gravity.START)
        }


        rows_gridIcon.setOnClickListener {
            if (restorePrefBool("isGrid")) {
                rvNotes.layoutManager = LinearLayoutManager(requireContext())
                savePrefsBool("isGrid" , false)
                rows_gridIcon.setImageResource(R.drawable.grid_icon)
            }else {
                rvNotes.layoutManager = StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL)
                savePrefsBool("isGrid" , true)
                rows_gridIcon.setImageResource(R.drawable.rows_icon)
            }
        }

        var job: Job? = null

        searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500L)
                it?.let {
                    if (searchEditText.text.isNotEmpty()){
                        viewModel.searchNote(searchEditText.text.toString())
                        Log.d("hhhhhh" , "Gasdad")
                    } else {
                        viewModel.getNotes()
                    }
                }
            }
            viewModel.searchQueryMutable.observe(viewLifecycleOwner, {
                Log.d("hhhhhh" , it.toString())
                noteAdapter.submitList(it)

            })

        }



    }

    private fun setupRecyclerView() {

        if (restorePrefBool("isGrid")) {
            rvNotes.layoutManager = StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL)
            rows_gridIcon.setImageResource(R.drawable.rows_icon)
        }else {
            rvNotes.layoutManager = LinearLayoutManager(requireContext())
            rows_gridIcon.setImageResource(R.drawable.grid_icon)
        }


    }

    fun restorePrefBool(key : String): Boolean {
        val pref = context!!.getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }

    private fun savePrefsBool(key : String , value : Boolean) {
        val pref = context!!.getSharedPreferences(
            "myPrefs",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

}
