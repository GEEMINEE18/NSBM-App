package com.example.mygreenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ButtonAdapter(private val mList: List<ButtonViewModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        val currentUrl: String = itemsViewModel.image

        // sets the image to the imageview from our itemHolder class
        Glide.with(holder.imageView.getContext())
            .load(currentUrl)
            .into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.titleView.text = itemsViewModel.title
        holder.urlList.text =itemsViewModel.url
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        private val cardView: CardView = itemView.findViewById(R.id.button_layout)
        val imageView: ImageView = itemView.findViewById(R.id.imgRecycler)
        val titleView: TextView = itemView.findViewById(R.id.txtRecycler)
        val urlList: TextView = itemView.findViewById(R.id.txtHiddenUrl)

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // take adapter position and text
            val position: Int = bindingAdapterPosition
            val text: String = titleView.text as String
            val url: String = urlList.text as String
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, text, url)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, text: String, url: String)
    }
}
