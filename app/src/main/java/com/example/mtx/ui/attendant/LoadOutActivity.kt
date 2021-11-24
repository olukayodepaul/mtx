package com.example.mtx.ui.attendant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivityLoadouttBinding
import com.example.mtx.ui.module.ModuleAdapter
import com.example.mtx.util.GeoFencing
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class LoadOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadouttBinding

    private lateinit var sessionManager: SessionManager

    private lateinit var adapter: LoadOutAdapter

    private val viewModel: AttendantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadouttBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        setSupportActionBar(binding.toolbar)
        initAdapter()
        refreshAdapter()
        basketResponse()
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclers.layoutManager = layoutManager
        binding.recyclers.setHasFixedSize(true)
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.isUserDailyBaskets( sessionManager.fetchEmployeeId.first(),
                sessionManager.fetchDate.first(),
                GeoFencing.currentDate!!
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clock_in -> {

            }
            R.id.clock_out -> {

            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.attendantmenu, menu)
        return true
    }

    private fun basketResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.basketResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {}

                        is NetworkResult.Error -> {}

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {
                            binding.loaders.isVisible = false

                            val limitToSalesEntry =  it.data!!.data!!.filter {
                                filters->filters.seperator.equals("1")
                            }

                            adapter = LoadOutAdapter(limitToSalesEntry)
                            adapter.notifyDataSetChanged()
                            binding.recyclers.setItemViewCacheSize(limitToSalesEntry.size)
                            binding.recyclers.adapter = adapter

                        }
                    }
                }
            }
        }
    }
}