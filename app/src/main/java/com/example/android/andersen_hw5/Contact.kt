package com.example.android.andersen_hw5

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(var name: String, var surname: String, var phoneNumber: String) : Parcelable