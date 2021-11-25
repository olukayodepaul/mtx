package com.example.mtx.ui.customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityAddCuctomerBinding

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCuctomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCuctomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}