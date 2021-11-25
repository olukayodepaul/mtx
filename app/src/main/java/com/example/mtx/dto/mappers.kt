package com.example.mtx.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

fun Customers.toSalesEntry() : CustomersList {
    return CustomersList(
        auto, dates, timeago, employee_id, urno, customerno, outletname, latitude, longitude, sort, notice, outlet_waiver,
        cust_token, defaulttoken, seq, modes, rep_id, outletclassid, outletlanguageid, outlettypeid, contactphone,
        contactname, outlet_pic, repname, volumeclass, employee_code, customer_code, distance, depotwaivers, spec, _id
    )
}

fun BasketLimit.toBasketLimit() : BasketLimitList {
    return BasketLimitList(
        auto, employee_id, product_id, product_code, qty, soq, order_sold, price, product_name, seperator, seperatorname,
        dates, pricing, inventory, orders, entry_time, controlpricing, controlinventory, controlorder,blimit
    )
}

fun BasketLimitList.toBasketToApi() : BasketLimit {
    return BasketLimit(
        auto, employee_id, product_id, product_code, qty, soq, order_sold, price, product_name, seperator, seperatorname,
        dates, pricing, inventory, orders, entry_time, controlpricing, controlinventory, controlorder,blimit
    )
}

data class IsAllCustomers(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("entries")
    var entries: List<CustomersList>? = null
)

data class SalesEntryMapperInterface(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("data")
    var data: List<BasketLimitList>? = null
)

@Parcelize
data class IsParcelable(
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("entry_time")
    var entry_time: String? = null,
    @SerializedName("entry_date")
    var entry_date: String? = null,
    @SerializedName("userToken")
    var userToken: String? = null,
    @SerializedName("uii")
    var uii: String? = null,
    @SerializedName("data")
    var data:CustomersList? = null
): Parcelable


fun UserSpinner.toSpinners() : UserSpinnerEntity {
    return UserSpinnerEntity(
        auto, id, name, sep
    )
}

data class SpinnerInterface(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("data")
    var data: List<UserSpinnerEntity>? = null
)


