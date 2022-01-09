package com.example.mtx.ui.orderpurchase


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.ItemRowParentBinding
import com.example.mtx.databinding.UserModulesAdapterBinding
import com.example.mtx.dto.Orders


open class OrderAdapter :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var mItems: List<Orders> = ArrayList()
    var onItemClick: ((Orders) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =   ItemRowParentBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(val binding: ItemRowParentBinding) : RecyclerView.ViewHolder(binding.root){

//        init {
//            binding.  setOnClickListener {
//                onItemClick?.invoke(mItems[adapterPosition])
//            }
//        }

        fun bind(item: Orders) {
            binding.contentTitle.text = item.outletname
            if(item.orderitems!!.isNotEmpty()) {
                val itemListAdapter = OrderItemAdapter(item.orderitems!!)
                val layoutManager = LinearLayoutManager(itemView.context,  LinearLayoutManager.HORIZONTAL,false)
                binding.childRecyclerView.layoutManager = layoutManager
                binding.childRecyclerView.adapter = itemListAdapter
            }
        }
     }

    fun addData(list: List<Orders>) {
        mItems = list
        notifyDataSetChanged()
    }

}