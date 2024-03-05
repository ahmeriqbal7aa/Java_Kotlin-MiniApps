package com.example.todolist

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_view.view.*

// TODO To make RecyclerView we need "Adapter" and "View Holder"
class Adapter(var cardData:List<CardInfo>) : RecyclerView.Adapter<Adapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var priority: TextView = itemView.priority
        var layout: LinearLayout = itemView.myCardViewLayout
    }

    // onCreateViewHolder() method take card_view xml file (xml file is of "Int" type)
    // RecyclerView or viewHolder don't accept "Int" File
    // This method works like an adapter, as it converts "Int File" according to RecyclerView's Type
    // Means it convert "Int" to Type "View". This will possible using LayoutInflater()

    // Working of this Method is to Set the View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return viewHolder(itemView)
    }

    // Working of this Method is to Set the Values OR Set the Data inside View (inside Card)
    // This method represents one particular card
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        when(cardData[position].priority.toLowerCase()) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#EDC988"))
            "low" -> holder.layout.setBackgroundColor(Color.parseColor("#00917C"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#800d91"))
        }
        holder.title.text = cardData[position].title
        holder.priority.text = cardData[position].priority
        holder.itemView.setOnClickListener {

            // Here we call "intent" which take us to next Activity
            // Here "this" will not run so we write "holder.itemView.context"
            var intent = Intent(holder.itemView.context,UpdateCard::class.java)
            // Below line will help to display text in "Update Details Card's" TextFields
            // We sending "id" of every task which is "position"
            // TODO which is helpful for Update Data
            intent.putExtra("id", position)
            // Activity starts from context
            holder.itemView.context.startActivity(intent)
        }
    }

    // Working of this Method is to Count how many Number of Items
    override fun getItemCount(): Int {
        return cardData.size
    }
}