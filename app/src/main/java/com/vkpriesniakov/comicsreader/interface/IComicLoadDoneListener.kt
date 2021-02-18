package com.vkpriesniakov.comicsreader.`interface`

import com.vkpriesniakov.comicsreader.model.MyComic

interface IComicLoadDoneListener {
    fun onComicLoadDoneListener(comicList:List<MyComic>)
}