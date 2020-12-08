package com.wsiz.projekt_zespolowy.base.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R

class HeaderRecycleViewAdapter : BaseAdapter<HeaderRecycleViewAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
        return HeaderViewHolder(view)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {}

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}