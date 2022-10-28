package com.example.mtx.ui.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivityMessageBinding
import com.example.mtx.dto.BreadCastNotification
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.isNotificationBroadCast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    private lateinit var adapter: MessageAdapter

    private val viewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initViewModels()
        isMessageAccuracyStateFlow()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclers.layoutManager = layoutManager
        binding.recyclers.setHasFixedSize(true)
    }

    private fun initViewModels() {
        viewModel.isMessageAccuracy("","")
    }

    private fun isMessageAccuracyStateFlow() =  lifecycleScope.launchWhenResumed {
        viewModel.messageResponseState.collect {
            it.let {
                when(it){
                    is NetworkResult.Empty -> {
                    }
                    is NetworkResult.Error -> {
                    }
                    is NetworkResult.Loading -> {
                    }
                    is NetworkResult.Success -> {
                        binding.progressbarHolder.isVisible = false
                        adapter = MessageAdapter(it.data!!)
                        adapter.notifyDataSetChanged()
                        binding.recyclers.setItemViewCacheSize(it.data.size)
                        binding.recyclers.adapter = adapter
                    }
                }
            }
        }
    }
}