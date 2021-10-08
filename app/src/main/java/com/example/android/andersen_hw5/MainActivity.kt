package com.example.android.andersen_hw5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.ContactClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance()
            replace(R.id.frame_layout, listFragment)
            commit()
        }
    }

    override fun onContactClicked(contact: Contact) {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance(contact)
            replace(R.id.frame_layout, contactFragment)
            addToBackStack("ContactFragment")
            commit()
        }
    }
}