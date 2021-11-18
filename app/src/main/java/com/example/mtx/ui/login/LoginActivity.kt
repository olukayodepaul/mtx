package com.example.mtx.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mtx.R
import com.example.mtx.databinding.ActivityMainBinding
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() , View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginid.setOnClickListener(this)
        loginStateFlow()
    }

    private fun setLogin() {
        val userName: String = binding.etUsername.text.toString()
        val password: String = binding.etPassword.text.toString()
        viewModel.fetchAllSalesEntries(userName, password)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.loginid -> {
                setLogin()
            }
        }
    }

    private fun loginStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.loginResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty->{
                        }

                        is NetworkResult.Error -> {
                            Toast.makeText(applicationContext, it.throwable!!.message.toString(), Toast.LENGTH_LONG).show()
                        }

                        is NetworkResult.Loading->{
                            Toast.makeText(applicationContext, "Loading", Toast.LENGTH_LONG).show()
                        }

                        is NetworkResult.Success->{
                            Toast.makeText(applicationContext, it.data!!.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }


}

