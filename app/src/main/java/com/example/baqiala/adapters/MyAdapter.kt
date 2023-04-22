package com.example.baqiala.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baqiala.R
import com.example.baqiala.data.Note

class MyAdapter(private val myDataset: List<Note>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    // Adapter implementation goes here
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_note_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = myDataset[position]
        holder.textView.text = note.name + note.hour + note.day
    }

    override fun getItemCount() = myDataset.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.my_text)
    }

}
