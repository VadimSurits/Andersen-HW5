package com.example.android.andersen_hw5

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class ContactFragment : Fragment(R.layout.fragment_contact) {

    private var contact: Contact? = null
    private lateinit var contactName: TextView
    private lateinit var contactSurname: TextView
    private lateinit var contactPhoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = arguments?.getParcelable(CONTACT_EXTRA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactName = view.findViewById(R.id.contact_name)
        contactSurname = view.findViewById(R.id.contact_surname)
        contactPhoneNumber = view.findViewById(R.id.contact_phone_number)
        contactName.text = contact?.name
        contactSurname.text = contact?.surname
        contactPhoneNumber.text = contact?.phoneNumber
    }

    companion object {
        private const val CONTACT_EXTRA = "CONTACT_EXTRA"

        fun newInstance(contact: Contact) = ContactFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(CONTACT_EXTRA, contact)
            }
        }
    }
}