package com.example.bloodbankapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.BloodBank

class BloodBankAdapter (
        private val context: Context,
        private val list: ArrayList<BloodBank>,
        private val mListener: OnItemClickListener,
    ) : RecyclerView.Adapter<BloodBankAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_blood_bank, parent, false)
            return MyViewHolder(view)
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(position)
        }
        override fun getItemCount() = list.size


        inner class MyViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
            @SuppressLint("SetTextI18n")
            fun bind(position: Int) {
                val name = root.findViewById<TextView>(R.id.bank_name)
                val rating = root.findViewById<TextView>(R.id.b_rating)
                val viewMore = root.findViewById<TextView>(R.id.view_more)
                val item = list[position]

                name.text = item.bankName
                rating.text = item.rating.toString()+ "/5"

                viewMore.setOnClickListener {
                    mListener.onItemClick(position)
                }
            }
        }

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
}