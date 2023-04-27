package com.example.baqiala.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baqiala.R
import com.example.baqiala.adapters.MyAdapter
import com.example.baqiala.databinding.ActivityMainBinding
import com.example.baqiala.databinding.MyNoteLayoutBinding
import com.example.baqiala.utils.SwipeToDeleteCallback
import com.example.baqiala.viewModel.MyViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var progressLayout: MyNoteLayoutBinding
    private lateinit var bottomSheetFragment: BottomSheetFragment
    private val viewModel by viewModels<MyViewModel>()
    private lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.recycler.layoutManager = LinearLayoutManager(this)
        viewModel.getAllNotes(this)
        viewModel.notes.observe(this) { data ->
            // update UI with data
            adapter = MyAdapter(data, this, viewModel)
            _binding.recycler.adapter = adapter
            val swipeToDeleteCallback = ContextCompat.getDrawable(this, R.drawable.delete_24)?.let {
                SwipeToDeleteCallback(adapter, it)
            }
            val itemTouchHelper = swipeToDeleteCallback?.let { ItemTouchHelper(it) }
            itemTouchHelper?.attachToRecyclerView(_binding.recycler)
        }

        _binding.fab.setOnClickListener {
            startActivity(Intent(this, AddNote::class.java))
        }
        _binding.settings.setOnClickListener { showBottomSheetSettings() }

    }

    private fun showBottomSheetSettings() {
        bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        bottomSheetFragment.isCancelable = true
        bottomSheetFragment.show(supportFragmentManager, BottomSheetFragment.TAG)
    }

  
}