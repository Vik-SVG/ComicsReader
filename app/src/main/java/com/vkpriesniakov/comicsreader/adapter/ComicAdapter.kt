package com.vkpriesniakov.comicsreader.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vkpriesniakov.comicsreader.ChapterActivity
import com.vkpriesniakov.comicsreader.`interface`.IRecyclerClick
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ComicItemBinding
import com.vkpriesniakov.comicsreader.model.MyComic

class ComicAdapter(
    private val context: Context,
    private val comicList: List<MyComic>
) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ComicViewHolder(private val bdnView: ComicItemBinding) :
        RecyclerView.ViewHolder(bdnView.root), View.OnClickListener {

        private lateinit var iRecyclerClick: IRecyclerClick

        fun setClick(iRecyclerClick: IRecyclerClick) {
            this.iRecyclerClick = iRecyclerClick
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            v?.let { iRecyclerClick.onClick(it, adapterPosition) }
        }

        fun bind(position: Int) {

            Picasso.get().load(comicList[position].Image)
                .into(bdnView.comicItemImage)

            bdnView.comicItemName.text = comicList[position].Name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicAdapter.ComicViewHolder {
        val bdn = ComicItemBinding.inflate(inflater, parent, false)
        return ComicViewHolder(bdn)
    }

    override fun onBindViewHolder(holder: ComicAdapter.ComicViewHolder, position: Int) {
        holder.bind(position)
        holder.setClick(object : IRecyclerClick {
            override fun onClick(view: View, position: Int) {
                Common.selected_comic = comicList[position]
                context.startActivity(Intent(context, ChapterActivity::class.java))
            }
        })
    }

    override fun getItemCount(): Int = comicList.size
}