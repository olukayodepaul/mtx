package com.example.mtx.ui.sales

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.databinding.SalesAdapterBinding
import com.example.mtx.dto.Customers

class SalesAdapter(private var mItems: List<Customers>, private val context: Context) :
    RecyclerView.Adapter<SalesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = SalesAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: SalesAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Customers) {

            val letter: String = item.outletname!!.substring(0, 1).toUpperCase()
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)
            binding.outletImageView.setImageDrawable(drawable)
            binding.tvCustName.text = item.outletname!!.toLowerCase().capitalize()
            binding.tvTitles.text = ("URNO: ${item.urno}, VCL: ${item.volumeclass}")
            binding.timeago.text = item.timeago

            if(item.sort==1) {
                binding.custIcons.isVisible = false
                binding.tvTitles.text = item.notice
                binding.tvSequence.isVisible = false
            }

            if(item.sort==2) {
                binding.iconsImageD.isVisible = false
            }

            if(item.sort==3) {
                binding.custIcons.isVisible = false
                binding.tvTitles.text = item.notice
                binding.tvSequence.isVisible = false
            }

            if(item.sort==4) {
                binding.custIcons.isVisible = false
                binding.tvTitles.text = item.notice
                binding.tvSequence.isVisible = false
            }
        }
    }
}