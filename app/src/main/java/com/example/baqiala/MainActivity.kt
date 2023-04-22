package com.example.baqiala

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.example.baqiala.adapters.MyAdapter
import com.example.baqiala.databinding.ActivityMainBinding
import com.example.baqiala.databinding.BottomSheetLayoutBinding
import com.example.baqiala.viewModel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.Month
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter: MyAdapter


    lateinit var bottomSheetFragment: BottomSheetFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]


        viewModel.notes.observe(this) {
            adapter = MyAdapter(it)
            _binding.recycler.adapter = adapter
        }

        /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             .setAction("Action", null).show()*/

        _binding.fab.setOnClickListener {
            /*bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogTheme)
            bottomSheetFragment.show(supportFragmentManager, "BSDialogFragment")*/

            startActivity(Intent(this, AddNote::class.java))

        }


    }


}