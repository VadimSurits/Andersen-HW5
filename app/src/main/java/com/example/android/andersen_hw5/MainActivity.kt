package com.example.android.andersen_hw5

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.ContactClickListener,
    ContactFragment.ButtonSaveClickListener {

    private lateinit var contactsList: ArrayList<Contact>
    private var isLandscape: Boolean = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        contactsList = createContactList()

        if (isLandscape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            startContactListFragment(R.id.fragment_list_land)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
            startContactListFragment(R.id.frame_layout)
        }
    }

    private fun startContactListFragment(layoutID: Int) {
        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance(contactsList)
            replace(layoutID, listFragment)
            commit()
        }
    }

    private fun startContactFragment(layoutID: Int, index: Int, contact: Contact) {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance(index, contact)
            replace(layoutID, contactFragment)
            addToBackStack("ContactFragment")
            commit()
        }
    }

    override fun onContactClicked(index: Int, contact: Contact) {
        if (isLandscape) {
            startContactFragment(R.id.fragment_contact_land, index, contact)
        } else {
            startContactFragment(R.id.frame_layout, index, contact)
        }
    }

    override fun onButtonSaveClicked(index: Int, contact: Contact) {
        contactsList[index].apply {
            name = contact.name
            surname = contact.surname
            phoneNumber = contact.phoneNumber
        }
        if (isLandscape) {
            startContactListFragment(R.id.fragment_list_land)
        }
        onBackPressed()
    }

    private fun createContactList(): ArrayList<Contact> {
        return arrayListOf(
            Contact("Иван", "Иванов", "+71111111111"),
            Contact("Пётр", "Петров", "+72222222222"),
            Contact("Андрей", "Андреев", "+73333333333"),
            Contact("Сергей", "Сергеев", "+74444444444")
        )
    }
}