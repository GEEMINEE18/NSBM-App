package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // Created this new function for this to work
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Code for the buttons inside the fragment
        //We have to add view.findViewById instead of findViewById
        val button = view.findViewById<Button>(R.id.clubSocietyBtn)
        val newsBtn = view.findViewById<Button>(R.id.newsBtn)

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider) // init imageSlider

        val reader = NewsReaderWriter(requireContext())

        val imageReference = reader.readFromImageArray()
        val imageList = ArrayList<SlideModel>()

        imageSlider.setOnClickListener {
            val intent = Intent (requireContext(),NewsPageActivity::class.java)
            startActivity(intent)
        }

        for (i in imageReference.indices){
            imageList.add(SlideModel(imageReference[i], "News $i"))
        }

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
                val intent = Intent (requireContext(),NewsPageActivity::class.java)
                startActivity(intent)
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN){
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP ) {
                    imageSlider.startSliding(1500)
                }
            }
        })

        button.setOnClickListener {
            //Intent works when we replace "this" with "requireContext()"
            val intent = Intent (requireContext(),ClubsAndSocietiesActivity::class.java)
            startActivity(intent)
        }

        newsBtn.setOnClickListener {
            val intent = Intent (requireContext(),NewsPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}