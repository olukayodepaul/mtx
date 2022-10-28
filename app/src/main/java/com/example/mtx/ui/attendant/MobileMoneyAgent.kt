package com.example.mtx.ui.attendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivityMobileMoneyAgentBinding
import com.example.mtx.databinding.BottomItemSheetBinding
import com.example.mtx.dto.IsMoneyAgent
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.ToastDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MobileMoneyAgent : AppCompatActivity() {

    private lateinit var binding: ActivityMobileMoneyAgentBinding


    private val viewModel: AttendantViewModel by viewModels()

    private lateinit var adapter: MobileMoneyAgentAdapter

    private lateinit var employeeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileMoneyAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initAdapter()
        employeeId = intent.getStringExtra("employeeId")!!
        Log.d("checkIntent", employeeId)
        viewModel.isMobileMoneyAgent(employeeId)
        agentStateFlow()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.retry -> {
                viewModel.isMobileMoneyAgent(employeeId)
            }
        }
        return false
    }


    private fun agentStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.isMoneyAgentsResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {}

                        is NetworkResult.Error -> {
                            ToastDialog(applicationContext, it.toString())
                            binding.progressbarHolder.isVisible = false
                        }

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {

                            val isCacheData = it.data!!

                            if(isCacheData.status ==200) {

                                adapter = MobileMoneyAgentAdapter(isCacheData.orderagent!!, applicationContext, ::handleAdapterEvent)
                                adapter.notifyDataSetChanged()
                                binding.recycler.setItemViewCacheSize(isCacheData.orderagent!!.size)
                                binding.recycler.adapter = adapter
                                binding.progressbarHolder.isVisible = false

                            }else {
                                ToastDialog(applicationContext, isCacheData.msg!!)
                                binding.progressbarHolder.isVisible = false
                            }

                        }
                    }
                }
            }
        }
    }


    private fun handleAdapterEvent(items: IsMoneyAgent) {

        val inflater = LayoutInflater.from(applicationContext)
        val bindings = BottomItemSheetBinding.inflate(inflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bindings.root)
        bindings.textView.text = items.agennama!!.toLowerCase().capitalize()
        bindings.textView2.text = items.phone
        bindings.textView4.text = items.address!!.toLowerCase().capitalize()
        dialog.show()

    }


}