package com.example.mtx.ui.salesentry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivitySalesEntryBinding
import com.example.mtx.ui.order.ReOrderActivity
import com.example.mtx.util.GeoFencing
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.example.mtx.util.ToastDialog
import com.nex3z.notificationbadge.NotificationBadge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@AndroidEntryPoint
class SalesEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesEntryBinding

    private val viewModel: SalesEntryViewModel by viewModels()

    private lateinit var adapter: SalesEntryAdapter

    private lateinit var sessionManager: SessionManager

    var notificationBadgeView: View? = null

    var notificationBadge: NotificationBadge? = null

    var item_Notification: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        sessionManager = SessionManager(this)
        initAdapter()
        refreshAdapter()
        basketResponse()
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.isUserDailyBaskets(sessionManager.fetchEmployeeId.first(), sessionManager.fetchDate.first(), GeoFencing.currentDate!!)
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.tvRecycler.layoutManager = layoutManager
        binding.tvRecycler.setHasFixedSize(true)
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

    private fun basketResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.basketResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {
                            //binding.loaders.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            //binding.loaders.isVisible = false
                            adapter = SalesEntryAdapter(it.data!!.data!!, applicationContext)
                            adapter.notifyDataSetChanged()
                            binding.tvRecycler.setItemViewCacheSize(it.data!!.data!!.size)
                            binding.tvRecycler.adapter = adapter
                        }
                    }
                }
            }
        }
    }
}