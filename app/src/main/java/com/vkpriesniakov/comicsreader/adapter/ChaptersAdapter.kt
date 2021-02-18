package com.vkpriesniakov.comicsreader.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkpriesniakov.comicsreader.ViewComicActivity
import com.vkpriesniakov.comicsreader.`interface`.IRecyclerClick
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ChapterItemBinding
import com.vkpriesniakov.comicsreader.model.Chapters

class ChaptersAdapter(
    private val context: Context,
    private val chapterList: List<Chapters>
) : RecyclerView.Adapter<ChaptersAdapter.ChaptersViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ChaptersViewHolder(private val bdn: ChapterItemBinding) :
        RecyclerView.ViewHolder(bdn.root), View.OnClickListener {

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
            bdn.textChapterNumber.text = StringBuilder(chapterList[position].Name)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        val bdn = ChapterItemBinding.inflate(inflater, parent, false)
        return ChaptersViewHolder(bdn)
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        holder.bind(position)

        holder.setClick(object : IRecyclerClick {
            override fun onClick(view: View, position: Int) {
                Common.selected_chapter = chapterList[position]
                Common.chapterIndex = position
                context.startActivity(Intent(context, ViewComicActivity::class.java))
            }
        })
    }

    override fun getItemCount(): Int = chapterList.size
}