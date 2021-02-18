package com.vkpriesniakov.comicsreader.model

data class MyComic(
    var Name: String? = null,
    var Image: String? = null,
    var Category: String? = null,
    var Chapters: List<Chapters>? = null
)