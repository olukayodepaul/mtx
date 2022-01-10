package com.example.mtx.ui.orderpurchase


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.ItemRowChildBinding
import com.example.mtx.dto.Orders

open class OrderItemAdapter(items: List<Orders.OrderItems>): RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    private var itemsList: List<Orders.OrderItems> = ArrayList()

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = ItemRowChildBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(itemsList[p1])
    }

    override fun getItemCount(): Int = itemsList.size

    inner class ViewHolder(private val binding: ItemRowChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Orders.OrderItems) {
            binding.name.text = item.product_code
        }
    }

}
