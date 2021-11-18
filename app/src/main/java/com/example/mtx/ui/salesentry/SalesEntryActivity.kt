package com.example.mtx.ui.salesentry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivitySalesEntryBinding
import dagger.hilt.android.AndroidEntryPoint

class SalesEntryActivity : AppCompatActivity() {

    lateinit var binding: ActivitySalesEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}