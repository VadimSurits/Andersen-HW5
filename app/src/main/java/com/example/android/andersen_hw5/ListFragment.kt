package com.example.android.andersen_hw5

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment(R.layout.fragment_list), RVAdapter.RecyclerViewItemClickListener {

    private lateinit var myList: MutableList<Contact>
    private lateinit var contactClickListener: ContactClickListener
    private var position: Int = 0

    companion object {
        private const val CONTACTS_LIST_EXTRA = "CONTACTS_LIST_EXTRA"
        private const val CONTACTS_LIST_POSITION_EXTRA = "CONTACTS_LIST_POSITION_EXTRA"

        fun newInstance(
            contactsList: MutableList<Contact>,
            position: Int
        ) = ListFragment().also {
            it.arguments = Bundle().apply {
                putParcelableArrayList(CONTACTS_LIST_EXTRA, contactsList as ArrayList<Contact>)
                putInt(CONTACTS_LIST_POSITION_EXTRA, position)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContactClickListener) {
            contactClickListener = context
        } else {
            throw IllegalArgumentException("host activity must implement ContactClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myList =
            arguments?.getParcelableArrayList<Contact>(CONTACTS_LIST_EXTRA) as ArrayList<Contact>
        position = arguments?.getInt(CONTACTS_LIST_POSITION_EXTRA, 0)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RVAdapter(myList, this)
        if (position != 0) {
            recyclerView.scrollToPosition(position)
        }
    }

    override fun onItemClicked(position: Int) {
        contactClickListener.onContactClicked(position, myList[position])
    }

    interface ContactClickListener {
        fun onContactClicked(position: Int, contact: Contact)
    }
}