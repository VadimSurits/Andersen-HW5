package com.example.android.andersen_hw5

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVAdapter(
    private var myList: MutableList<Contact>,
    private val myRVItemClickListener: RecyclerViewItemClickListener,
    private val myRVItemLongClickListener: RecyclerViewLongItemClickListener
) : RecyclerView.Adapter<RVAdapter.MyViewHolder>(), Filterable {

    private var myFilterList = mutableListOf<Contact>()
    private var isFilteringNow = false

    init {
        myFilterList = myList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.contactName?.text = myFilterList[position].name
        holder.contactSurname?.text = myFilterList[position].surname
        holder.contactPhoneNumber?.text = myFilterList[position].phoneNumber
        holder.contactImage?.let {
            Glide.with(holder.itemView.context)
                .load("https://picsum.photos/200/?temp=$position")
                .into(it)
        }
        holder.itemClicked(
            myRVItemClickListener,
            position,
            myFilterList[position],
            myFilterList,
            myList
        )
        holder.itemLongClicked(myRVItemLongClickListener, position)
    }

    override fun getItemCount() = myFilterList.size

    fun deleteItem(position: Int) {
        var currentContact: Contact? = null
        if (position == myFilterList.size - 1 || position == 0) {
            myList.forEach {
                if (myFilterList[position].name == it.name &&
                    myFilterList[position].surname == it.surname &&
                    myFilterList[position].phoneNumber == it.phoneNumber
                ) {
                    currentContact = it
                }
            }
            myFilterList.removeAt(position)
            notifyItemRemoved(position)
        } else {
            var shift = 1
            while (true) {
                try {
                    myList.forEach {
                        if (myFilterList[position - shift].name == it.name &&
                            myFilterList[position - shift].surname == it.surname &&
                            myFilterList[position - shift].phoneNumber == it.phoneNumber
                        ) {
                            currentContact = it
                        }
                    }
                    myFilterList.removeAt(position - shift)
                    notifyItemRemoved(position)
                    break
                } catch (e: IndexOutOfBoundsException) {
                    shift++
                }
            }
        }
        myList.remove(currentContact)
        if (!isFilteringNow) {
            myList = myFilterList
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    isFilteringNow = false
                    myFilterList = myList
                } else {
                    isFilteringNow = true
                    val resultList = mutableListOf<Contact>()
                    myList.forEach {
                        if (it.name.lowercase().contains(charSearch.lowercase())
                            || it.surname.lowercase().contains(charSearch.lowercase())
                        ) {
                            resultList.add(it)
                        }
                    }
                    myFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = myFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                myFilterList = results?.values as MutableList<Contact>
                notifyDataSetChanged()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var contactName: TextView? = null
        var contactSurname: TextView? = null
        var contactPhoneNumber: TextView? = null
        var contactImage: ImageView? = null

        init {
            contactName = itemView.findViewById(R.id.contact_name)
            contactSurname = itemView.findViewById(R.id.contact_surname)
            contactPhoneNumber = itemView.findViewById(R.id.contact_phone_number)
            contactImage = itemView.findViewById(R.id.contact_image)
        }

        fun itemClicked(
            myRVItemClickListener: RecyclerViewItemClickListener,
            position: Int,
            contact: Contact,
            contactListForChange: MutableList<Contact>,
            mainList: MutableList<Contact>
        ) {
            itemView.setOnClickListener {
                myRVItemClickListener.onItemClicked(
                    position,
                    contact,
                    contactListForChange,
                    mainList
                )
            }
        }

        fun itemLongClicked(
            myRVItemLongClickListener: RecyclerViewLongItemClickListener,
            position: Int
        ) {
            itemView.setOnLongClickListener {
                myRVItemLongClickListener.onItemLongClicked(position, it)
                true
            }
        }
    }

    interface RecyclerViewLongItemClickListener {
        fun onItemLongClicked(position: Int, view: View)
    }

    interface RecyclerViewItemClickListener {
        fun onItemClicked(
            position: Int,
            contact: Contact,
            contactListForChange: MutableList<Contact>,
            mainList: MutableList<Contact>,
        )
    }
}