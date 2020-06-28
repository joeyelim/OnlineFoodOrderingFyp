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
import com.example.fyp.Class.*
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

//        var cartList = ArrayList<Cart>()
//
//        val date = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm") //or use getDateInstance()
//        val formatedDate = formatter.format(date)
//
//        cartList.add(Cart("Shredded Mushrooms Lou Syu Fan", 4.80, 1, "Remark",
//            "Canteen1", "Noodle", formatedDate ))

        //--------------------------------------------yien----------------------------------------------------------

        var order = ArrayList<Order>()

        order.add(Order("26 June 2020", "26 June 2020", "Dine-In", "Pending",
            "2:30 PM", "2:30 PM", 9.0, "O100001"))

        order.add(Order("26 June 2020", "26 June 2020", "Take Away", "Pending",
            "2:40 PM", "2:40 PM", 13.0, "O100002"))

        var cartList = ArrayList<Cart>()

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm") //or use getDateInstance()
        val formatedDate = formatter.format(date)

        cartList.add(Cart("Spicy Prawn and Tofu Ramen Noodle Soup", 5.50, 1, "NO.",
            "Red Bricks Cafeteria", "Noodle", formatedDate ))

//        cartList.add(Cart("Ayam Goreng Berempah", 5.50, 1, "NO.",
//            "Yum Yum Cafeteria", "Masakan Malaysia", formatedDate ))
//
//        cartList.add(Cart("Bihun Goreng", 3.90, 1, "NO.",
//            "Red Bricks Cafeteria", "Mamak", formatedDate ))

//        cartList.add(Cart("Cafe Americano", 4.80, 2, "NO.",
//            "Red Bricks Cafeteria", "Cafe", formatedDate ))


        var orderfood = ArrayList<Order_Food>()

        orderfood.add(Order_Food("Stewed Sweet and Sour Pork (咕噜肉饭)", 5.5, "Pending", 2,
            "Add-on tomato source", "Red Bricks Cafeteria", "Mixed Rice", "Dine-In"))

        var orderfood2 = ArrayList<Order_Food>()
        orderfood2.add(Order_Food("Ristetto Coffee Latte", 6.5, "Pending", 2,
            "need 3 tsp. sugar & milk", "Red Bricks Cafeteria", "Cafe", "Dine-In"))


//        var store = ArrayList<CanteenStore>()
//
//        store.add(CanteenStore("Cafe", "Store/cafe.jpg", "Cafe"))
//        store.add(CanteenStore("Vegeterian", "Store/vegeterian.png", "Vegeterian"))






//        var cartList = ArrayList<Cart>()
//
//        val date = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm") //or use getDateInstance()
//        val formatedDate = formatter.format(date)
//
//        cartList.add(Cart("Shredded Mushrooms Lou Syu Fan", 4.80, 1, "Remark",
//            "Canteen1", "Noodle", formatedDate ))




//        var orderFoodList = ArrayList<Order_Food>()
//
//        for (x in 1 until 3) {
//            val orderFood = Order_Food(
//                "Food_Name_Preparing", x + 6.5, "Ready", x,
//                "Ready", "CanteenName", "StoreName", "Dine In"
//            )
//
//            orderFoodList.add(orderFood)
//        }


//        for ((index, item) in cartList.withIndex()) {
//            val foodInt = (index)
//            db.collection("User").document("Yong Boon")
//                .collection("Cart").document(item.Cart_ID!!)
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

//--------------------------------------------yien----------------------------------------------------------

//        for ((index, item) in order.withIndex()) {
//            val foodInt = (index)
//            db.collection("User").document("limye-wm18@student.tarc.edu.my")
//                .collection("Order").document(item.id!!)
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
//
//        for ((index, item) in cartList.withIndex()) {
//            val foodInt = (index)
//            db.collection("User").document("limye-wm18@student.tarc.edu.my")
//                .collection("Cart").document(item.cart_ID!!)
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

        for ((index, item) in orderfood.withIndex()) {
            val foodInt = (index)
            db.collection("User").document("limye-wm18@student.tarc.edu.my")
                .collection("Order").document("O100001")
                .collection("Order_Food").document(item.food_Name!!)
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


        for ((index, item) in orderfood2.withIndex()) {
            val foodInt = (index)
            db.collection("User").document("limye-wm18@student.tarc.edu.my")
                .collection("Order").document("O100002")
                .collection("Order_Food").document(item.food_Name!!)
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
