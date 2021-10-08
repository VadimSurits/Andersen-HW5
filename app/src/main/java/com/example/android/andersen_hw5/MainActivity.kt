package com.example.android.andersen_hw5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.ContactClickListener,
    ContactFragment.ButtonSaveClickListener {

    private lateinit var contactsList: ArrayList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsList = createContactList()

        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance(contactsList)
            replace(R.id.frame_layout, listFragment)
            commit()
        }
    }

    override fun onContactClicked(index: Int, contact: Contact) {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance(index, contact)
            replace(R.id.frame_layout, contactFragment)
            addToBackStack("ContactFragment")
            commit()
        }
    }

    override fun onButtonSaveClicked(index: Int, contact: Contact) {
        contactsList[index].apply {
            name = contact.name
            surname = contact.surname
            phoneNumber = contact.phoneNumber
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