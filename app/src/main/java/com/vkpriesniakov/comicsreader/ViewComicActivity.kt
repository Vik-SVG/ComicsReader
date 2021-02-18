package com.vkpriesniakov.comicsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.vkpriesniakov.comicsreader.adapter.MyViewPagerAdapter
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ViewComicActivityBinding
import com.vkpriesniakov.comicsreader.model.Chapters
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2

class ViewComicActivity : AppCompatActivity() {

    private lateinit var bdn: ViewComicActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bdn = ViewComicActivityBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)

        bdn.comicViewBackItem.setOnClickListener {
            if (Common.chapterIndex == 0) {
                Toast.makeText(this@ViewComicActivity, "First chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex = 0
                fetchLinks(Common.selected_chapter!!)
            }
        }

        bdn.comicViewNextItem.setOnClickListener {
            if (Common.chapterIndex == Common.chapterList!!.size - 1) {
                Toast.makeText(this@ViewComicActivity, "Last chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex = Common.selected_chapter!!.Links!!.size - 1
                fetchLinks(Common.selected_chapter!!)
            }
        }

        fetchLinks(Common.selected_chapter!!)
    }

    private fun fetchLinks(chapters: Chapters) {
        if (chapters.Links != null) {
            if (chapters.Links!!.isNotEmpty()) {

                val mAdapter = MyViewPagerAdapter(baseContext, chapters.Links!!)
                bdn.comicViewPager.adapter = mAdapter
                bdn.txtChapterNameComicView.text =
                    Common.formatString(Common.selected_chapter!!.Name!!)

                bdn.comicViewPager.currentItem = Common.chapterIndex

                val pageFlipper = BookFlipPageTransformer2()
                pageFlipper.scaleAmountPercent = 10F
                bdn.comicViewPager.setPageTransformer(pageFlipper)

                bdn.comicViewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Common.chapterIndex = position
                    }
                })
            } else {
                Toast.makeText(this@ViewComicActivity, "No chapters here", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this@ViewComicActivity, "Latest chapter", Toast.LENGTH_SHORT).show()
        }


    }
}