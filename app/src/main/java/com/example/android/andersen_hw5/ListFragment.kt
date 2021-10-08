package com.example.android.andersen_hw5

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var contactOne: LinearLayoutCompat
    private lateinit var contactTwo: LinearLayoutCompat
    private lateinit var contactThree: LinearLayoutCompat
    private lateinit var contactFour: LinearLayoutCompat
    private lateinit var contactOneName: TextView
    private lateinit var contactOneSurname: TextView
    private lateinit var contactOnePhoneNumber: TextView
    private lateinit var contactTwoName: TextView
    private lateinit var contactTwoSurname: TextView
    private lateinit var contactTwoPhoneNumber: TextView
    private lateinit var contactThreeName: TextView
    private lateinit var contactThreeSurname: TextView
    private lateinit var contactThreePhoneNumber: TextView
    private lateinit var contactFourName: TextView
    private lateinit var contactFourSurname: TextView
    private lateinit var contactFourPhoneNumber: TextView

    private lateinit var myList: ArrayList<Contact>
    private lateinit var contactClickListener: ContactClickListener

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
    }

    companion object {
        private const val CONTACTS_LIST_EXTRA = "CONTACTS_LIST_EXTRA"

        fun newInstance(contactsList: ArrayList<Contact>) = ListFragment().also {
            it.arguments = Bundle().apply {
                putParcelableArrayList(CONTACTS_LIST_EXTRA, contactsList)
            }
        }
    }

    private fun initUI(view: View) {
        contactOneName = view.findViewById(R.id.contact_one_name)
        contactOneName.text = myList[0].name
        contactOneSurname = view.findViewById(R.id.contact_one_surname)
        contactOneSurname.text = myList[0].surname
        contactOnePhoneNumber = view.findViewById(R.id.contact_one_phone_number)
        contactOnePhoneNumber.text = myList[0].phoneNumber

        contactTwoName = view.findViewById(R.id.contact_two_name)
        contactTwoName.text = myList[1].name
        contactTwoSurname = view.findViewById(R.id.contact_two_surname)
        contactTwoSurname.text = myList[1].surname
        contactTwoPhoneNumber = view.findViewById(R.id.contact_two_phone_number)
        contactTwoPhoneNumber.text = myList[1].phoneNumber

        contactThreeName = view.findViewById(R.id.contact_three_name)
        contactThreeName.text = myList[2].name
        contactThreeSurname = view.findViewById(R.id.contact_three_surname)
        contactThreeSurname.text = myList[2].surname
        contactThreePhoneNumber = view.findViewById(R.id.contact_three_phone_number)
        contactThreePhoneNumber.text = myList[2].phoneNumber

        contactFourName = view.findViewById(R.id.contact_four_name)
        contactFourName.text = myList[3].name
        contactFourSurname = view.findViewById(R.id.contact_four_surname)
        contactFourSurname.text = myList[3].surname
        contactFourPhoneNumber = view.findViewById(R.id.contact_four_phone_number)
        contactFourPhoneNumber.text = myList[3].phoneNumber

        contactOne = view.findViewById(R.id.contact_one)
        contactOne.setOnClickListener {
            contactClickListener.onContactClicked(0, myList[0])
        }

        contactTwo = view.findViewById(R.id.contact_two)
        contactTwo.setOnClickListener {
            contactClickListener.onContactClicked(1, myList[1])
        }

        contactThree = view.findViewById(R.id.contact_three)
        contactThree.setOnClickListener {
            contactClickListener.onContactClicked(2, myList[2])
        }

        contactFour = view.findViewById(R.id.contact_four)
        contactFour.setOnClickListener {
            contactClickListener.onContactClicked(3, myList[3])
        }
    }

    interface ContactClickListener {
        fun onContactClicked(index: Int, contact: Contact)
    }
}