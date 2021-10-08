package com.example.android.andersen_hw5

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(val name: String, val surname: String, val phoneNumber: String) : Parcelable