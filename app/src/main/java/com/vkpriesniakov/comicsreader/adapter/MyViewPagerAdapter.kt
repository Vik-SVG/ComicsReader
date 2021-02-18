package com.vkpriesniakov.comicsreader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vkpriesniakov.comicsreader.databinding.ViewPagerItemBinding

class MyViewPagerAdapter(private val context: Context,
                         private var linksList: List<String>) :
    RecyclerView.Adapter<MyViewPagerAdapter.PagerHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class PagerHolder (private val bdn:ViewPagerItemBinding): RecyclerView.ViewHolder(bdn.root) {
        fun bind(position: Int) {
            Picasso.get().load(linksList[position]).into(bdn.pagerImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHolder {
        val bdn = ViewPagerItemBinding.inflate(inflater, parent, false)
        return PagerHolder(bdn)
    }

    override fun onBindViewHolder(holder: PagerHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = linksList.size



}
