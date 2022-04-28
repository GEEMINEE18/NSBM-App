package com.example.mygreenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val mList: ArrayList<NewsViewModel>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        val currentUrl: String = itemsViewModel.image

        // sets the image to the imageview from our itemHolder class
        Glide.with(holder.imageView.context)
            .load(currentUrl)
            .into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.titleView.text = itemsViewModel.title
        holder.descriptionView.text = itemsViewModel.description

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgWeb)
        val titleView: TextView = itemView.findViewById(R.id.txtWebTitle)
        val descriptionView: TextView = itemView.findViewById(R.id.txtWebDescription)
    }
}
