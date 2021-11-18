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

