package com.example.android.andersen_hw5

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment(R.layout.fragment_list), RVAdapter.RecyclerViewItemClickListener,
    RVAdapter.RecyclerViewLongItemClickListener {

    private lateinit var myList: MutableList<Contact>
    private lateinit var contactClickListener: ContactClickListener
    private var position: Int = 0

    private lateinit var mySearchFilter: SearchView
    private var adapter: RVAdapter? = null

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
        adapter = RVAdapter(myList, this, this)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_drawable
            )!!
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        if (position != 0) {
            recyclerView.scrollToPosition(position)
        }

        mySearchFilter = view.findViewById(R.id.search_filter)
        mySearchFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })
    }

    override fun onItemClicked(
        position: Int,
        contact: Contact,
        contactListForChange: MutableList<Contact>,
        mainList: MutableList<Contact>,
    ) {
        contactClickListener.onContactClicked(position, contact, contactListForChange, mainList)
    }

    override fun onItemLongClicked(position: Int, view: View) {
        val myPopupMenu = PopupMenu(requireContext(), view)
        myPopupMenu.inflate(R.menu.popup_menu)
        myPopupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    adapter?.deleteItem(position)
                }
            }
            true
        }
        myPopupMenu.show()
    }

    interface ContactClickListener {
        fun onContactClicked(
            position: Int,
            contact: Contact,
            contactListForChange: MutableList<Contact>,
            mainList: MutableList<Contact>,
        )
    }
}