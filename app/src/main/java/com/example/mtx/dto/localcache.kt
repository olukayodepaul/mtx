package com.example.mtx.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "customersdetails")
data class CustomersListEntity (
    @PrimaryKey(autoGenerate = false)
    var auto: Int = 0,
    var outlet_id: Int = 0,
    var urno: Int = 0,
    var customerno: String? = null,
    var outletclass_id: Int = 0,
    var outletlanguage_id: Int = 0,
    var outlettype_id: Int = 0,
    var outletname: String = "",
    var outletaddress: String = "",
    var contactname: String = "",
    var contactphone: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var posting_date: String = "",
    var entry_date: String = "",
    var outlet_waiver: String = "",
    var cust_token: String = "",
    var defaulttoken: String = "",
    var volumeclass: String = "",
    var salesentrytime: String = "00:00:00"
): Parcelable