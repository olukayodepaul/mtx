package com.example.mtx.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityReOrderBinding

class ReOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}