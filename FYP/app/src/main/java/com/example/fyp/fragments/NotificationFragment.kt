package com.example.fyp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import com.example.fyp.databinding.FragmentNotificationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_place_order_progress2.*
import java.util.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_notification, container, false
        )

        binding.btnUploadFirestore.setOnClickListener {
            uploadData()
//            updateData()
        }

        binding.btnDeleteFirestore.setOnClickListener {
            deleteData()
        }

//        binding.btnUploadFirestorage.setOnClickListener {
//            uploadPhoto()
//        }
//
//        binding.btnUploadFirestorage.setOnClickListener {
//            deletePhoto()
//        }

        // [START storage_field_initialization]
        storage = Firebase.storage
        // [END storage_field_initialization]

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
    private fun uploadData() {
        var dataList = ArrayList<Canteen>()

        for (x in 10 until 20) {
            var testData = Canteen("time", "image", x.toString(),"Canteen")
            dataList.add(testData)
        }

        val db = FirebaseFirestore.getInstance()

//        for ((index, data) in dataList.withIndex()) {
//            db.collection("Canteen").document("Canteen$index")
//                .collection("nested1").document("Food$index")
//                .set(data)
//                .addOnSuccessListener {
//                    Log.i("upload", "success")
//                }
//                .addOnFailureListener {
//                    Log.i("upload", "Error adding document", it)
//                }
//                .addOnCompleteListener {
//                    Toast.makeText(activity, "Finish Upload Data!", Toast.LENGTH_SHORT).show();
//                }
//        }


//        val order = Order("Pick__Date", "Order_Date", "Take_Away",
//            "Pending", "Pick_Time", "Order_Time", "Total_Price")

//        var orderFood = ArrayList<Order>()
//
//        for (x in 1 until 3) {
//            val order = Order(
//                "12/12/2020", "15/12/2020", "Option", "status",
//                "15:00", "12:00", "Total_Order"
//            )
//
//            orderFood.add(order)
//        }
//
//        for ((index, item) in orderFood.withIndex()) {
//            var foodIndex = index + 5
//            db.collection("User").document("Yong Boon")
//                .collection("Order").document("Order$foodIndex")
//                .set(item)
//                .addOnSuccessListener {
//                    Log.i("upload", "success")
//                }
//                .addOnFailureListener {
//                    Log.i("upload", "Error adding document", it)
//                }
//                .addOnCompleteListener {
//                    Toast.makeText(activity, "Finish Upload Data!", Toast.LENGTH_SHORT).show()
//                }
//        }

        var orderFoodList = ArrayList<Order_Food>()

        for (x in 1 until 3) {
            val orderFood = Order_Food(
                "Food_Name_Preparing", x + 6.5, "Ready", x,
                "Ready", "CanteenName", "StoreName", "Dine In"
            )

            orderFoodList.add(orderFood)
        }

        for ((index, item) in orderFoodList.withIndex()) {
            val foodInt = (index)
            db.collection("User").document("Yong Boon")
                .collection("Order").document("Order5")
                .collection("Order_Food").document("OrderFood$foodInt")
                .set(item)
                .addOnSuccessListener {
                    Log.i("upload", "success")
                }
                .addOnFailureListener {
                    Log.i("upload", "Error adding document", it)
                }
                .addOnCompleteListener {
                    Toast.makeText(activity, "Finish Upload Data!", Toast.LENGTH_SHORT).show()
                }
        }


    }


    private fun deleteData() {
        val db = FirebaseFirestore.getInstance()

        db.collection("Test2").document("Canteen0")
            .delete()
            .addOnSuccessListener { Log.d("delete", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("delete", "Error deleting document", e) }
    }

    private fun updateData() {
        val db = FirebaseFirestore.getInstance()


        db.collection("Test2").document("Canteen0")
            .update("time", "lllllllllll")
            .addOnSuccessListener {
                Log.i("upload", "success")
            }
            .addOnFailureListener {
                Log.i("upload", "Error adding document", it)
            }
            .addOnCompleteListener {
                Toast.makeText(activity, "Finish Upload Data!", Toast.LENGTH_SHORT).show();
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

//    private fun uploadPhoto() {
//
//// Create a storage reference from our app
//        val storageRef = storage.reference
//
//// Create a reference to "mountains.jpg"
//        val mountainsRef = storageRef.child("mountains.jpg")
//
//// Create a reference to 'images/mountains.jpg'
//        val mountainImagesRef = storageRef.child("test/mountains.jpg")
//
//// While the file names are the same, the references point to different files
//        mountainsRef.name == mountainImagesRef.name // true
//        mountainsRef.path == mountainImagesRef.path // false
//    }
//
//    private fun deletePhoto() {
//
//    }


}
