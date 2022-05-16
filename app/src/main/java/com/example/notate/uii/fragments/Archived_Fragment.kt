package com.example.notate.uii.fragments

import NoteAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notate.viewModel.NoteViewModel
import com.example.notate.R
import com.example.notate.repository.Repository
import com.example.notate.viewModel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_archived.*

class Archived_Fragment : Fragment(R.layout.fragment_archived) {

    val noteAdapter: NoteAdapter by lazy {
        NoteAdapter()
    }

    val staggeredGridLayoutManager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    lateinit var viewModel: NoteViewModel

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val repository = Repository()
        val viewModelFactor = NoteViewModelFactory(repository , requireContext())
        viewModel = ViewModelProvider(this , viewModelFactor).get(NoteViewModel::class.java)

        viewModel.getArchivedData()

        viewModel.archivedNotesMutable.observe(requireActivity(), androidx.lifecycle.Observer {
            noteAdapter.submitList(it)
        })

        noteAdapter.setOnItemClickListner {

            it.update = true
            val action = Archived_FragmentDirections.actionArchivedFragmentToDetailesFragment(it)
            findNavController().navigate(action)


        }


        ar_MenuIcon.setOnClickListener {
            this.requireActivity().drawer_layout.openDrawer(Gravity.START)
        }

        ar_rows_gridIcon.setOnClickListener {
            if (restorePrefBool("isGrid")) {
                rvArchived.layoutManager = linearLayoutManager
                savePrefsBool("isGrid" , false)
                ar_rows_gridIcon.setImageResource(R.drawable.grid_icon)
            }else {
                rvArchived.layoutManager = staggeredGridLayoutManager
                savePrefsBool("isGrid" , true)
                ar_rows_gridIcon.setImageResource(R.drawable.rows_icon)
            }
        }




    }

    private fun setupRecyclerView() {

        if (restorePrefBool("isGrid")) {
            rvArchived.layoutManager = staggeredGridLayoutManager
            ar_rows_gridIcon.setImageResource(R.drawable.rows_icon)
        }else {
            rvArchived.layoutManager = linearLayoutManager
            ar_rows_gridIcon.setImageResource(R.drawable.grid_icon)
        }

        rvArchived.adapter = noteAdapter

    }

    fun restorePrefBool(key : String): Boolean {
        val pref = requireContext().getSharedPreferences("archivedPrefs", AppCompatActivity.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }

    private fun savePrefsBool(key : String , value : Boolean) {
        val pref = requireContext().getSharedPreferences(
            "archivedPrefs",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

}