package com.example.mtx.ui.module

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivityModulesBinding
import com.example.mtx.ui.order.ReOrderActivity
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.example.mtx.util.ToastDialog
import com.nex3z.notificationbadge.NotificationBadge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class ModulesActivity : AppCompatActivity() {

    lateinit var binding: ActivityModulesBinding

    private val viewModel: ModulesViewModel by viewModels()

    private lateinit var adapters: ModuleAdapter

    private lateinit var sessionManager: SessionManager

    var searchView: SearchView? = null

    var notificationBadgeView: View? = null

    var notificationBadge: NotificationBadge? = null

    var item_Notification: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModulesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        sessionManager = SessionManager(this)
        initAdapter()
        refreshAdapter()
        userModulesResponse()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclers.layoutManager = layoutManager
        binding.recyclers.setHasFixedSize(true)
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchAllSalesEntries(sessionManager.fetchEmployeeId.first())
        }
    }

    private fun userModulesResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.userModulesResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {
                            binding.loaders.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            binding.loaders.isVisible = false
                            adapters = ModuleAdapter(it.data!!.modules!!, applicationContext)
                            adapters.notifyDataSetChanged()
                            binding.recyclers.setItemViewCacheSize(it.data!!.modules!!.size)
                            binding.recyclers.adapter = adapters
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_search -> {

            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visitdetails, menu)
        item_Notification = menu!!.findItem(R.id.action_notifications)
        notificationBadgeView = item_Notification!!.actionView
        notificationBadge = notificationBadgeView!!.findViewById(R.id.badge) as NotificationBadge

        notificationBadgeView!!.setOnClickListener {
            val intent = Intent(applicationContext, ReOrderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        setupBadge()
        return true
    }

    private fun setupBadge() {
        notificationBadge!!.isVisible = true
        notificationBadge!!.setText("10")
    }
}