package com.example.mtx.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class ToastDialog (context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}


