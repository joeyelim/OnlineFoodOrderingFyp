package com.example.fyp

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.canteen_row.view.*

class MyAdapter {

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Model) {

            itemView.txtCanteen.text = model.title
            itemView.txtDescription.text = model.des
            itemView.imgCanteen.setImageResource(model.image)
            itemView.imgIcon.setImageResource(model.image)
        }
    }
}