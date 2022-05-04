package com.example.bloodbankapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.AmbulanceData

class AmbulanceAdapter (
        private val context: Context,
        private val list: ArrayList<AmbulanceData>,
        private val mListener: OnItemClickListener,
    ) : RecyclerView.Adapter<AmbulanceAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_ambulance, parent, false)
            return MyViewHolder(view)
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(position)
        }
        override fun getItemCount() = list.size


        inner class MyViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
            @SuppressLint("SetTextI18n")
            fun bind(position: Int) {
                val name = root.findViewById<TextView>(R.id.ambulance_name)
                val rating = root.findViewById<TextView>(R.id.a_rating)
                val availStatus = root.findViewById<TextView>(R.id.avail_state)
                val phone = root.findViewById<TextView>(R.id.txt_phone)
                val call = root.findViewById<TextView>(R.id.call_btn)
                val item = list[position]

                name.text = item.ambulanceName
                rating.text = item.rating.toString()+ "/5"
                availStatus.text = item.availStatus.toString()
                phone.text = item.phone.toString()

                call.setOnClickListener {
                    mListener.onItemClick(position)
                }
            }
        }

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
}