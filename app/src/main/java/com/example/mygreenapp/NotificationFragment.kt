package com.example.mygreenapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    // Database reference
    private lateinit var fStore: FirebaseFirestore
    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
    }
















    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email
        val userId = firebaseUser.uid

        fStore = FirebaseFirestore.getInstance()
        // Get the document [userId] in the collection "users"
        fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            val doc = task.result
            // Get the "following" list of the current user into an array
            val followList = doc.get("following") as ArrayList<*>
            // Database reference to the "meeting" collection
            val meetingRef=  fStore.collection("meeting")

            for (i in 0 until followList.size) {
                val query = meetingRef.whereEqualTo("clubName", followList[i])
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        // id is the meeting id
                        println(document.id)
                        // data is an array which contains all the data inside the meeting
                        println(document.data)
                    }
                }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents: ", exception)
                    }
            }


        }





        /*val listSize = titleList.size




        // RecyclerView

        // getting the recyclerview by its id
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerNotification)

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ArrayList of class ItemsViewModel
        val data = ArrayList<NotificationViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 0 until listSize) {
            data.add(NotificationViewModel(imageList[i], titleList[i], descriptionList[i]))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = NewsAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter*/
    }





















    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}