package com.example.myatm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myatm.databinding.TransactionListBinding
import com.example.myatm.roomDB.TransactionModel
import kotlin.properties.Delegates

class TransactionListAdapter() : RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {

    private var transactionList: ArrayList<TransactionModel> by Delegates.observable(arrayListOf()) { p, oldList, newList ->
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id== newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        TransactionListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = transactionList.size

    inner class ViewHolder(private val binding: TransactionListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.transaction = transactionList[position]
        }
    }

    fun setData(transactionList:ArrayList<TransactionModel>){
        this.transactionList = transactionList
    }
}