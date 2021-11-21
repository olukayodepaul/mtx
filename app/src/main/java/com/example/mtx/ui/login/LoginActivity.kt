package com.example.mtx.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mtx.R
import com.example.mtx.databinding.ActivityMainBinding
import com.example.mtx.ui.module.ModulesActivity
import com.example.mtx.util.GeoFencing
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.example.mtx.util.ToastDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() , View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        binding.loginid.setOnClickListener(this)
        loginStateFlow()
    }

    private fun setLogin() {

        val  userName: String = binding.etUsername.text.toString()
        val  password: String = binding.etPassword.text.toString()

        if(userName.isEmpty() || password.isEmpty()){
            ToastDialog(applicationContext, "Enter username and password").toast
        }else{
            lifecycleScope.launchWhenResumed {
                if (sessionManager.fetchPassword.first() ==  password
                    && sessionManager.fetchUsername.first() == userName
                    && sessionManager.fetchDate.first() == GeoFencing.currentDate){

                    val intent = Intent(applicationContext, ModulesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }else{
                    viewModel.fetchAllSalesEntries(userName, password)
                }
            }
        }
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
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading->{
                            binding.loader.isVisible = true
                        }

                        is NetworkResult.Success -> {

                            binding.loader.isVisible = false

                            if (it.data!!.status == 200) {
                                if (arrayOf(it.data.login!!).isEmpty()) {
                                    ToastDialog(applicationContext, "Error, Critical error. User not define").toast
                                } else {

                                    sessionManager.deleteStore()
                                    sessionManager.storeEmployeeId(it.data.login!!.employee_id!!)
                                    sessionManager.storeUsername(it.data.login!!.username!!)
                                    sessionManager.storePassword(it.data.login!!.password!!)
                                    sessionManager.storeDate(it.data.login!!.dates!!)

                                    val intent = Intent(applicationContext, ModulesActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()

                                }
                            } else {
                                ToastDialog(applicationContext, it.data.msg!!).toast
                            }
                        }
                    }
                }
            }
        }
    }


}

