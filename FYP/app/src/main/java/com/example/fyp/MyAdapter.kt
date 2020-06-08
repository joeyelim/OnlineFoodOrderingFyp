package com.example.fyp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.canteen_row.view.*

class MyAdapter (val arrayList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

//    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
//        fun bindItems(model: Model) {
//
//            itemView.txtCanteen.text = model.title
//            itemView.txtDescription.text = model.des
//            itemView.imgCanteen.setImageResource(model.image)
//            itemView.imgIcon.setImageResource(model.image)
//        }
//    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: Model) {

            itemView.txtCanteen.text = model.title
            itemView.txtDescription.text = model.des
            itemView.imgCanteen.setImageResource(model.image)
            itemView.imgIcon.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.canteen_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])


    }
}