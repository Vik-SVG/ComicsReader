package com.vkpriesniakov.comicsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vkpriesniakov.comicsreader.adapter.ChaptersAdapter
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ActivityChapterBinding
import com.vkpriesniakov.comicsreader.model.MyComic

class ChapterActivity : AppCompatActivity() {

    private lateinit var bdn: ActivityChapterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityChapterBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)

        bdn.comicToolbar.title = Common.selected_comic?.Name ?: ""
        bdn.comicToolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
        bdn.comicToolbar.setNavigationOnClickListener {
            finish()
        }

        bdn.chapterRv.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        bdn.chapterRv.layoutManager = LinearLayoutManager(this)
        bdn.chapterRv.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        Common.selected_comic?.let { fetchChapter(it) }

    }

    private fun fetchChapter(selectedComic: MyComic) {
        Common.chapterList = selectedComic.Chapters
        bdn.chapterTxt.text =
            StringBuilder("Chapters (")
                .append(selectedComic.Chapters?.size)
                .append(")")
        bdn.chapterRv.adapter = selectedComic.Chapters?.let { ChaptersAdapter(this, it) }

//        bdn.chapterRv.adapter = ChaptersAdapter(this, selectedComic!!.Chapters!!)
    }
}
