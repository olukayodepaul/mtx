package com.example.mtx.ui.attendant


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.MoneyAgentBinding
import com.example.mtx.dto.IsMoneyAgent


class MobileMoneyAgentAdapter (private var iSAgent: List<IsMoneyAgent>): RecyclerView.Adapter<MobileMoneyAgentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =   MoneyAgentBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = iSAgent[p1]
        p0.bind(item)
    }

    override fun getItemCount() = iSAgent.size

    inner class ViewHolder(private val binding: MoneyAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IsMoneyAgent) {

        }
    }
}