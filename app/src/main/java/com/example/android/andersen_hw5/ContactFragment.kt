package com.example.android.andersen_hw5

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment

class ContactFragment : Fragment(R.layout.fragment_contact) {

    private var contact: Contact? = null
    private var index: Int? = null
    private lateinit var contactName: AppCompatEditText
    private lateinit var contactSurname: AppCompatEditText
    private lateinit var contactPhoneNumber: AppCompatEditText
    private lateinit var buttonSave: AppCompatButton

    private lateinit var buttonSaveClickListener: ButtonSaveClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonSaveClickListener) {
            buttonSaveClickListener = context
        } else {
            throw IllegalArgumentException("host activity must implement ContactClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = arguments?.getParcelable(CONTACT_EXTRA)
        index = arguments?.getInt(CONTACT_INDEX_EXTRA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactName = view.findViewById(R.id.contact_name)
        contactSurname = view.findViewById(R.id.contact_surname)
        contactPhoneNumber = view.findViewById(R.id.contact_phone_number)
        contactName.setText(contact?.name)
        contactSurname.setText(contact?.surname)
        contactPhoneNumber.setText(contact?.phoneNumber)

        buttonSave = view.findViewById(R.id.btn_save)
        buttonSave.setOnClickListener {
            val changedContact = Contact(
                contactName.text.toString(),
                contactSurname.text.toString(),
                contactPhoneNumber.text.toString()
            )
            index?.let { index ->
                buttonSaveClickListener.onButtonSaveClicked(index, changedContact)
            }
        }
    }

    companion object {
        private const val CONTACT_EXTRA = "CONTACT_EXTRA"
        private const val CONTACT_INDEX_EXTRA = "CONTACT_INDEX_EXTRA"

        fun newInstance(index: Int, contact: Contact) = ContactFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(CONTACT_EXTRA, contact)
                putInt(CONTACT_INDEX_EXTRA, index)
            }
        }
    }

    interface ButtonSaveClickListener {
        fun onButtonSaveClicked(index: Int, contact: Contact)
    }
}