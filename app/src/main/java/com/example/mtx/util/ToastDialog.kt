package com.example.mtx.util

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mtx.dto.BreadCastNotification

class ToastDialog (context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

var isNotificationBroadCast: MutableLiveData<BreadCastNotification>? = null