package com.example.mygreenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val mList: List<NotificationViewModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.clubView.text = itemsViewModel.clubName
        holder.titleView.text = itemsViewModel.title
        holder.descriptionView.text = itemsViewModel.description
        holder.dateView.text = itemsViewModel.date
        holder.timeView.text = itemsViewModel.time
        holder.venueView.text = itemsViewModel.venue
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val clubView: TextView = itemView.findViewById(R.id.txtClubName)
        val titleView: TextView = itemView.findViewById(R.id.txtMeetingTitle)
        val descriptionView: TextView = itemView.findViewById(R.id.txtMeetingDescription)
        val dateView: TextView = itemView.findViewById(R.id.txtMeetingDate)
        val timeView: TextView = itemView.findViewById(R.id.txtMeetingTime)
        val venueView: TextView = itemView.findViewById(R.id.txtMeetingVenue)
    }
}
