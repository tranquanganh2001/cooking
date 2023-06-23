package com.example.appcookiesremakekotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.appcookiesremakekotlin.activities.AddNotesActivity
import com.example.appcookiesremakekotlin.adapters.NoteAdapter
import com.example.appcookiesremakekotlin.databinding.FragmentNotesBinding
import com.example.appcookiesremakekotlin.utils.Constants
import com.example.appnoteskotlin.db.NoteDatabase


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val noteDB: NoteDatabase by lazy {
        Room.databaseBuilder(requireContext(), NoteDatabase::class.java, Constants.NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val noteAdapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageAddNotesMain.setOnClickListener {
            startActivity(Intent(context, AddNotesActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if(noteDB.noteDao().getAllNotes().isNotEmpty()) {
                notesRecyclerView.visibility = View.VISIBLE
                noteAdapter.differ.submitList(noteDB.noteDao().getAllNotes())
                setUpRecyclerView()
            } else {
                notesRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }
}