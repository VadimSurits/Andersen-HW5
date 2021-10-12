package com.example.android.andersen_hw5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVAdapter(
    private val myList: List<Contact>,
    private val myRVItemClickListener: RecyclerViewItemClickListener
) :
    RecyclerView.Adapter<RVAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.contactName?.text = myList[position].name
        holder.contactSurname?.text = myList[position].surname
        holder.contactPhoneNumber?.text = myList[position].phoneNumber
        holder.contactImage?.let {
            Glide.with(holder.itemView.context)
                .load("https://picsum.photos/200/?temp=$position")
                .into(it)
        }
        holder.bind(myRVItemClickListener, position)
    }

    override fun getItemCount() = myList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemLayout: ConstraintLayout? = null
        var contactName: TextView? = null
        var contactSurname: TextView? = null
        var contactPhoneNumber: TextView? = null
        var contactImage: ImageView? = null

        init {
            itemLayout = itemView.findViewById(R.id.rv_contact_item)
            contactName = itemView.findViewById(R.id.contact_name)
            contactSurname = itemView.findViewById(R.id.contact_surname)
            contactPhoneNumber = itemView.findViewById(R.id.contact_phone_number)
            contactImage = itemView.findViewById(R.id.contact_image)
        }

        fun bind(myRVItemClickListener: RecyclerViewItemClickListener, position: Int) {
            itemView.setOnClickListener {
                myRVItemClickListener.onItemClicked(position)
            }
        }
    }

    interface RecyclerViewItemClickListener {
        fun onItemClicked(position: Int)
    }
}