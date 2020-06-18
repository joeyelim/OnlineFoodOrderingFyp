package com.example.fyp.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fyp.Class.Canteen

import com.example.fyp.R
import com.example.fyp.databinding.FragmentNotificationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.opencensus.tags.Tag

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {
    private lateinit var binding : FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notification, container, false
        )

        binding.btnUploadFirestore.setOnClickListener {
            uploadData()
        }

        return binding.root
    }

    // upload single data
//    private fun uploadData(){
//        var testData = Canteen("time", "image", "name")
//
//        val db = FirebaseFirestore.getInstance()
//        db.collection("Canteen").document("Cantten4").set(testData)
//            .addOnSuccessListener {
//                Log.i("upload", "success")
//            }
//            .addOnFailureListener {
//                Log.i("upload", "Error adding document", it)
//            }
//    }

    // upload multuple data
    private fun uploadData(){
        var dataList = ArrayList<Canteen>()

        for (x in 10 until 20) {
            var testData = Canteen("time", "image", x.toString())
            dataList.add(testData)
        }

        val db = FirebaseFirestore.getInstance()

        for ((index, data) in dataList.withIndex())
        {
            db.collection("Test2").document("Canteen$index").set(data)
                .addOnSuccessListener {
                    Log.i("upload", "success")
                }
                .addOnFailureListener {
                    Log.i("upload", "Error adding document", it)
                }
                .addOnCompleteListener {
                    Toast.makeText(getActivity(),"Finish Upload Data!",Toast.LENGTH_SHORT).show();
                }
        }


    }


}
