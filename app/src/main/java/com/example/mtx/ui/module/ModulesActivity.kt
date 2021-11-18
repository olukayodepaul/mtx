package com.example.mtx.ui.module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mtx.databinding.ActivityModulesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModulesActivity : AppCompatActivity() {

    lateinit var binding: ActivityModulesBinding

    private val viewModel : ModulesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModulesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}