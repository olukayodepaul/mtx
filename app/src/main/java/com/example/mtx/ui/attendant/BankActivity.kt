package com.example.mtx.ui.attendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityBankBinding

class BankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}