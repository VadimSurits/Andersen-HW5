package com.example.android.andersen_hw5

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.ContactClickListener,
    ContactFragment.ButtonSaveClickListener {

    private lateinit var contactsList: MutableList<Contact>
    private var isLandscape: Boolean = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        contactsList = createContactList()

        if (isLandscape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            startContactListFragment(R.id.fragment_list_land, 0)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
            startContactListFragment(R.id.frame_layout, 0)
        }
    }

    private fun startContactListFragment(layoutID: Int, position: Int) {
        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance(contactsList, position)
            replace(layoutID, listFragment)
            commit()
        }
    }

    private fun startContactFragment(
        layoutID: Int,
        position: Int,
        contact: Contact,
        contactListForChange: MutableList<Contact>,
        mainList: MutableList<Contact>
    ) {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance(
                position, contact,
                contactListForChange, mainList
            )
            replace(layoutID, contactFragment)
            addToBackStack("ContactFragment")
            commit()
        }
    }

    override fun onContactClicked(
        position: Int,
        contact: Contact,
        contactListForChange: MutableList<Contact>,
        mainList: MutableList<Contact>
    ) {
        if (isLandscape) {
            startContactFragment(
                R.id.fragment_contact_land, position, contact,
                contactListForChange, mainList
            )
        } else {
            startContactFragment(
                R.id.frame_layout, position, contact, contactListForChange,
                mainList
            )
        }
    }

    override fun onButtonSaveClicked(
        position: Int,
        contact: Contact,
        contactListForChange: MutableList<Contact>,
        mainList: MutableList<Contact>
    ) {
        var sameContactInMainList = contactListForChange[position]
        mainList.forEach {
            if (contactListForChange[position].name == it.name &&
                contactListForChange[position].surname == it.surname &&
                contactListForChange[position].phoneNumber == it.phoneNumber
            ) {
                sameContactInMainList = it
            }
        }
        contactListForChange[position].apply {
            name = contact.name
            surname = contact.surname
            phoneNumber = contact.phoneNumber
        }
        mainList.forEach {
            if (it == sameContactInMainList) {
                it.name = contact.name
                it.surname = contact.surname
                it.phoneNumber = contact.phoneNumber
            }
        }
        if (isLandscape) {
            startContactListFragment(R.id.fragment_list_land, position)
        }
        onBackPressed()
    }

    private fun createContactList(): MutableList<Contact> {
        val myList = mutableListOf<Contact>()
        for (i in 1..120) {
            val name = "Имя $i"
            val surname = "Фамилия $i"
            val phoneNumber = (89241111100 + (11 * i)).toString()
            myList.add(Contact(name, surname, phoneNumber))
        }
        return myList
    }
}