package com.example.mtx.ui.attendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityLoadInBinding
import com.example.mtx.databinding.ActivityLoadouttBinding

class LoadInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}