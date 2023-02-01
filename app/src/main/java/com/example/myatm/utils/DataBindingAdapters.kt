package com.example.myatm.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myatm.roomDB.TransactionModel
import com.example.myatm.ui.adapter.TransactionListAdapter


@BindingAdapter("app:total_amount")
fun TextView.formatText(full_text: Int) {
    text = "Rs. ${full_text}"
}

@BindingAdapter("itemViewModels")
fun bindItemViewModels(recyclerView: RecyclerView, itemViewModels: List<TransactionModel>?) {
    val adapter =
        if (recyclerView.adapter != null && recyclerView.adapter is TransactionListAdapter) {
            recyclerView.adapter as TransactionListAdapter
        } else {
            val transactionListAdapter = TransactionListAdapter()
            recyclerView.adapter = transactionListAdapter
            transactionListAdapter
        }
    itemViewModels?.let {
        adapter.setData(ArrayList(it))
    } ?: run {
        adapter.setData(arrayListOf())
    }
}