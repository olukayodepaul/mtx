package com.example.mtx.ui.orderpurchase


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.ItemRowParentBinding
import com.example.mtx.dto.Orders


open class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var mItems: List<Orders> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =   ItemRowParentBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item, p1)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(val binding: ItemRowParentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Orders, p1: Int) {

            binding.contentTitle.text = item.remark
            binding.timeago.text = item.etime

            if(item.order!!.isNotEmpty()) {
                val itemListAdapter = OrderItemAdapter(item.order!!)
                val layoutManager = LinearLayoutManager(itemView.context)
                binding.childRecyclerView.layoutManager = layoutManager
                binding.childRecyclerView.adapter = itemListAdapter
            }

            val isExpandable: Boolean = item.expandable!!

            binding.childRecyclerView.isVisible = isExpandable

            binding.contentTitle.setOnClickListener {
                item.expandable = !item.expandable!!
                notifyItemChanged(p1)
            }

        }
     }

    fun addData(list: List<Orders>) {
        mItems = list
        notifyDataSetChanged()
    }

}