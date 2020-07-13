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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.Food
import com.example.fyp.Class.Notification
import com.example.fyp.FirestoreAdapter.NotificationFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick4
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentNotificationBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlin.collections.ArrayList
import java.util.*



/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment(), onListClick4 {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var storage: FirebaseStorage
    private var adapter: NotificationFirestoreAdapter? = null
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_notification, container, false
        )

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        checkLogin()
        initRecycleView()

        binding.fabBtn.setOnClickListener{
            it.findNavController()
                .navigate(NotificationFragmentDirections.actionNotificationFragmentToStaffCreatePostFragment())
        }


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


    private fun initRecycleView() {

        val db = FirebaseFirestore.getInstance()

        try {
            val query = db.collection("User").document(userViewModel.user?.email!!)
                .collection("Notification")
                .orderBy("notif_ID", Query.Direction.ASCENDING)

            query.addSnapshotListener { p0, _ ->
                if (p0 != null) {

                    if (p0.size() > 0) {
                        binding.textView3.visibility = View.GONE
                        binding.rvNotification.visibility = View.VISIBLE
                    } else {
                        binding.textView3.visibility = View.VISIBLE
                        binding.rvNotification.visibility = View.GONE
                        binding.textView3.setText("There are currently no any notification yet.")
                    }
                }
            }

            val options =
                FirestoreRecyclerOptions.Builder<Notification>()
                    .setQuery(query, Notification::class.java).build()

            adapter = NotificationFirestoreAdapter(options, this, context!!)
            binding.rvNotification.layoutManager = LinearLayoutManager(activity)
            binding.rvNotification.adapter = adapter

            // Staff view
            if (userViewModel.user?.role == "staff") {
                binding.fabBtn.visibility = View.VISIBLE
            }
            else {
                binding.fabBtn.visibility = View.GONE
            }
        } catch (e: Exception) {

        }
    }

    override fun onStart() {
        super.onStart()
        if (userViewModel.user!!.email != "") {
            adapter?.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (userViewModel.user!!.email != "") {
            adapter?.stopListening()
        }
    }

    override fun onItemClick(notif: Notification, position: Int) {
        userViewModel.notification = notif
        this.findNavController()
            .navigate(NotificationFragmentDirections.actionNotificationFragmentToNotificationDetailsFragment())
    }

    private fun checkLogin() {
        if (Firebase.auth.currentUser == null || userViewModel.user?.email == "") {
            findNavController().navigate(NotificationFragmentDirections.actionNotificationFragmentToFragmentHome())
        }
    }














//-----------------------------------------------------------------------------------------------------------


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
            var testData = Canteen("time", "image", x.toString(), "Canteen")
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

        var food = ArrayList<Food>()
        var catarray= ArrayList<String>()
        catarray.add("Rice")
        catarray.add("Vegetarian")

        var cat2array= ArrayList<String>()
        cat2array.add("Noodle")
        cat2array.add("Vegetarian")

        food.add(
            Food(
                "Stewed Mixed Vegetables Rice", "Food/vegetables_rice.jpg", 4.5,5.0,
                "Braised assorted vegetable w/mushroom buddha′s feast（stewed mixed vegetable）vegetarian meal for buddhism",
            2, 3, 15,catarray
            )
        )

        food.add(
            Food(
                "Wonton Noodles", "Food/wanta mee.jpg", 4.8,5.2,
                "Vegetarian/vegan cuisine can be delicious, creative and healthy too. The wanton noodle " +
                        "is does not have meat / fish and no egg.  All whole-grain flour in baking, mostly vegan. Some diary would replace with non diary-milk, vegan cheese, and butter.",
                2, 3, 10,cat2array
            )
        )





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


//        for ((index, item) in food.withIndex()) {
//            val foodInt = (index)
//            db.collection("Canteen").document("Red Bricks Cafeteria")
//                .collection("Store").document("Vegetarian")
//                .collection("Food").document(item.food_name!!)
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
