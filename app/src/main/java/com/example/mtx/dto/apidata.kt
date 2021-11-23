package com.example.mtx.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("msg")
    @Expose
    var msg: String? = null,
    @SerializedName("login")
    @Expose
    var login: Employees? = null
)

data class Employees(
    @SerializedName("depots_id")
    @Expose
    var depots_id: Int? = null,
    @SerializedName("region_id")
    @Expose
    var region_id: Int? = null,
    @SerializedName("dates")
    @Expose
    var dates: String? = null,
    @SerializedName("syscategory_id")
    @Expose
    var syscategory_id: Int? = null,
    @SerializedName("employee_id")
    @Expose
    var employee_id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("employee_code")
    @Expose
    var employee_code: String? = null,
    @SerializedName("phone_imei")
    @Expose
    var phone_imei: String? = null,
    @SerializedName("username")
    @Expose
    var username: String? = null,
    @SerializedName("password")
    @Expose
    var password: String? = null
)

data class ModulesResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("msg")
    @Expose
    var msg: String? = null,
    @SerializedName("modules")
    @Expose
    var modules: List<UserModules>? = null
)

data class UserModules(
    @SerializedName("dates")
    @Expose
    var dates: String? = null,
    @SerializedName("employee_id")
    @Expose
    var employee_id: Int? = null,
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("navigationid")
    @Expose
    var navigationid: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("imageurl")
    @Expose
    var imageurl: String? = null
)

data class CustomersResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("msg")
    @Expose
    var msg: String? = null,
    @SerializedName("customers")
    @Expose
    var customers: List<Customers>? = null
)

data class Customers(
    @SerializedName("auto")
    @Expose
    var auto: Int = 0,
    @SerializedName("dates")
    @Expose
    var dates: String? = null,
    @SerializedName("timeago")
    @Expose
    var timeago: String? = null,
    @SerializedName("employee_id")
    @Expose
    var employee_id: Int? = null,
    @SerializedName("urno")
    @Expose
    var urno: Int? = null,
    @SerializedName("customerno")
    @Expose
    var customerno: String? = null,
    @SerializedName("outletname")
    @Expose
    var outletname: String? = null,
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null,
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null,
    @SerializedName("sort")
    @Expose
    var sort: Int? = null,
    @SerializedName("notice")
    @Expose
    var notice: String? = null,
    @SerializedName("outlet_waiver")
    @Expose
    var outlet_waiver: String? = null,
    @SerializedName("cust_token")
    @Expose
    var cust_token: String? = null,
    @SerializedName("defaulttoken")
    @Expose
    var defaulttoken: String? = null,
    @SerializedName("seq")
    @Expose
    var seq: String? = null,
    @SerializedName("modes")
    @Expose
    var modes: Int? = null,
    @SerializedName("rep_id")
    @Expose
    var rep_id: Int? = null,
    @SerializedName("outletclassid")
    @Expose
    var outletclassid: Int? = null,
    @SerializedName("outletlanguageid")
    @Expose
    var outletlanguageid: Int? = null,
    @SerializedName("outlettypeid")
    @Expose
    var outlettypeid: Int? = null,
    @SerializedName("contactphone")
    @Expose
    var contactphone: String? = null,
    @SerializedName("contactname")
    @Expose
    var contactname: String? = null,
    @SerializedName("outlet_pic")
    @Expose
    var outlet_pic: String? = null,
    @SerializedName("repname")
    @Expose
    var repname: String? = null,
    @SerializedName("volumeclass")
    @Expose
    var volumeclass: String? = null,
    @SerializedName("employee_code")
    @Expose
    var employee_code: String? = null,
    @SerializedName("customer_code")
    @Expose
    var customer_code: String? = null,
    @SerializedName("distance")
    @Expose
    var distance: String? = null,
    @SerializedName("duration")
    @Expose
    var duration: String? = null,
    @SerializedName("depotwaivers")
    @Expose
    var depotwaivers: String? = null,
    @SerializedName("spec")
    @Expose
    var spec: String? = null,
    @SerializedName("_id")
    @Expose
    var _id: String? = null
)

data class BasketLimitResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("msg")
    @Expose
    var msg: String? = null,
    @SerializedName("basketlimit")
    @Expose
    var basketlimit: List<BasketLimit>? = null
)

data class BasketLimit (
    @SerializedName("auto")
    @Expose
    var auto: Int = 0,
    @SerializedName("employee_id")
    @Expose
    var employee_id: Int? = null,
    @SerializedName("product_id")
    @Expose
    var product_id: Int? = null,
    @SerializedName("product_code")
    @Expose
    var product_code: String? = null,
    @SerializedName("qty")
    @Expose
    var qty: Float? = null,
    @SerializedName("soq")
    @Expose
    var soq: String? = null,
    @SerializedName("order_sold")
    @Expose
    var order_sold: Float? = null,
    @SerializedName("price")
    @Expose
    var price: Float? = null,
    @SerializedName("product_name")
    @Expose
    var product_name: String? = null,
    @SerializedName("seperator")
    @Expose
    var seperator: String? = null,
    @SerializedName("seperatorname")
    @Expose
    var seperatorname: String? = null,
    @SerializedName("dates")
    @Expose
    var dates: String? = null,
    @SerializedName("pricing")
    @Expose
    var pricing: Float? = null,
    @SerializedName("inventory")
    @Expose
    var inventory: Float? = null,
    @SerializedName("orders")
    @Expose
    var orders: Float? = null,
    @SerializedName("entry_time")
    @Expose
    var entry_time: String? = null,
    @SerializedName("controlpricing")
    @Expose
    var controlpricing: String? = null,
    @SerializedName("controlinventory")
    @Expose
    var controlinventory: String? = null,
    @SerializedName("controlorder")
    @Expose
    var controlorder: String? = null,
    @SerializedName("blimit")
    @Expose
    var blimit: String? = null
)

data class PostSalesResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("msg")
    @Expose
    var msg: String? = null
)

data class OrderPosted(
    @SerializedName("clat")
    @Expose
    var clat: String? = null,
    @SerializedName("clng")
    @Expose
    var clng: String? = null,

    @SerializedName("etime")
    @Expose
    var etime: String? = null,

    @SerializedName("edate")
    @Expose
    var edate: String? = null,

    @SerializedName("uiid")
    @Expose
    var uiid: String? = null,

    @SerializedName("employee_id")
    @Expose
    var employee_id: Int? = null,

    @SerializedName("urno")
    @Expose
    var urno: Int? = null,

    @SerializedName("customerno")
    @Expose
    var customerno: String? = null,

    @SerializedName("outletname")
    @Expose
    var outletname: String? = null,

    @SerializedName("outletlatitude")
    @Expose
    var outletlatitude: String? = null,

    @SerializedName("outletlongitude")
    @Expose
    var outletlongitude: String? = null,

    @SerializedName("volumeclass")
    @Expose
    var volumeclass: String? = null,
    @SerializedName("token")
    @Expose
    var token: String? = null,

    @SerializedName("order")
    @Expose
    var order: List<BasketLimit>? = null,

)











