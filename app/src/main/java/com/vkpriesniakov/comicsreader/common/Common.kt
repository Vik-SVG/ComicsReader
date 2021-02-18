package com.vkpriesniakov.comicsreader.common

import com.vkpriesniakov.comicsreader.model.Chapters
import com.vkpriesniakov.comicsreader.model.MyComic
import java.lang.StringBuilder

object Common {
    fun formatString(name: String): String {
        val finalResult = StringBuilder(
            if (name.length > 15)
                name.substring(0, 15) + "..." else name
        )
        return finalResult.toString()
    }

    val categories = arrayOf(
        "Action",
        "Adult",
        "Adventure",
        "Comedy",
        "Completed",
        "Cooking",
        "Doujinshi",
        "Drama",
        "Drop",
        "Ecchi",
        "Fantasy",
        "Gender bender",
        "Harem",
        "Historical",
        "Horror",
        "Jose",
        "Latest",
        "Manhua",
        "Manhwa",
        "Material arts",
        "Mature",
        "Mecha",
        "Medical",
        "Mystery",
        "Newest",
        "One shot",
        "Ongoing",
        "Psychological",
        "Romance",
        "School life",
        "Sci fi",
        "Seinen",
        "Shoujo",
        "Shoujo a",
        "Shounen",
        "Shounen ai",
        "Slice of life",
        "Smut",
        "Sports",
        "Superhero",
        "Supernatural",
        "Top Read",
        "Tragedy",
        "Webtoons",
        "Yaoi",
        "Yuri"
    )
    var chapterIndex: Int = -1
    var selected_chapter: Chapters? = null
    var chapterList: List<Chapters>? = null
    var selected_comic: MyComic? = null
    var comicList: List<MyComic> = ArrayList()
}
