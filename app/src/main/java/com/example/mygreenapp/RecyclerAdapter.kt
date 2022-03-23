package com.example.mygreenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // Create an object of NewsPageActivity
    var webInfo = GetSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemImage.setImageResource(webInfo.getImageList()[position].toInt())
        holder.itemTitle.text = webInfo.getTitleList()[position]
    }

    override fun getItemCount(): Int {
        return webInfo.getTitleList().size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView = itemView.findViewById(R.id.imgWeb)
        var itemTitle: TextView = itemView.findViewById(R.id.txtWebTitle)
        var itemDetails: TextView = itemView.findViewById(R.id.txtWebDescription)

    }
}
