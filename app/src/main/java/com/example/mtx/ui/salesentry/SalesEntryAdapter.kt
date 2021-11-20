package com.example.mtx.ui.salesentry

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.UserModulesAdapterBinding
import com.example.mtx.dto.BasketLimitList


class SalesEntryAdapter(private var mItems: List<BasketLimitList>, private val context: Context) :
    RecyclerView.Adapter<SalesEntryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = UserModulesAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: UserModulesAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BasketLimitList) {
            binding.modulecontents.text = item.product_name
        }
    }


}