package com.example.mtx.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "customers")
data class CustomersList (
    @PrimaryKey(autoGenerate = false)
    var auto: Int = 0,
    var dates: String? = null,
    var timeago: String? = null,
    var employee_id: Int? = null,
    var urno: Int? = null,
    var customerno: String? = null,
    var outletname: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var sort: Int? = null,
    var notice: String? = null,
    var outlet_waiver: String? = null,
    var cust_token: String? = null,
    var defaulttoken: String? = null,
    var seq: String? = null,
    var modes: Int? = null,
    var rep_id: Int? = null,
    var outletclassid: Int? = null,
    var outletlanguageid: Int? = null,
    var outlettypeid: Int? = null,
    var contactphone: String? = null,
    var contactname: String? = null,
    var outlet_pic: String? = null,
    var repname: String? = null,
    var volumeclass: String? = null,
    var employee_code: String? = null,
    var customer_code: String? = null,
    var distance: String? = null,
    var duration: String? = null,
    var depotwaivers: String? = null,
    var spec: String? = null,
    var _id: String? = null
): Parcelable

@Entity(tableName = "osqty")
data class BasketLimitList (
    @PrimaryKey(autoGenerate = false)
    var auto: Int = 0,
    var employee_id: Int? = null,
    var product_id: Int? = null,
    var product_code: String? = null,
    var qty: Float? = null,
    var soq: String? = null,
    var order_sold: Float? = null,
    var price: Float? = null,
    var product_name: String? = null,
    var seperator: String? = null,
    var seperatorname: String? = null,
    var dates: String? = null,
    var pricing: Float? = null,
    var inventory: Float? = null,
    var orders: Float? = null,
    var entry_time: String? = null,
    var controlpricing: String? = null,
    var controlinventory: String? = null,

)