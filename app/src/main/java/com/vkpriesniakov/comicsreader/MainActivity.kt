package com.vkpriesniakov.comicsreader

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.vkpriesniakov.comicsreader.`interface`.IBannerLoadDoneListener
import com.vkpriesniakov.comicsreader.`interface`.IComicLoadDoneListener
import com.vkpriesniakov.comicsreader.adapter.ComicAdapter
import com.vkpriesniakov.comicsreader.adapter.MySliderAdapter
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ActivityMainBinding
import com.vkpriesniakov.comicsreader.model.MyComic
import com.vkpriesniakov.comicsreader.services.ImageService
import dmax.dialog.SpotsDialog
import ss.com.bannerslider.Slider
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), IBannerLoadDoneListener, IComicLoadDoneListener {

    private lateinit var bdn: ActivityMainBinding
    private lateinit var bannersDb: DatabaseReference
    private lateinit var comicDb: DatabaseReference
    private lateinit var alertDialog: AlertDialog

    lateinit var iBannerLoadDoneListener: IBannerLoadDoneListener
    lateinit var iComicLoadDoneListener: IComicLoadDoneListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityMainBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)

        iBannerLoadDoneListener = this
        iComicLoadDoneListener = this

        alertDialog = SpotsDialog.Builder().setContext(this@MainActivity)
            .setCancelable(false)
            .setMessage("Please wait...")
            .build()

        bannersDb = FirebaseDatabase.getInstance().getReference("Banners")
        comicDb = FirebaseDatabase.getInstance().getReference("Comic")

//        preloadImages(Common.comicList)

        bdn.swipeToRefresh.setColorSchemeResources(R.color.primaryColorLight, R.color.primaryColor)
        bdn.swipeToRefresh.setOnRefreshListener {
            loadBanners()
            loadComic()
        }

        bdn.swipeToRefresh.post {
            loadBanners()
            loadComic()
        }

        Slider.init(ImageService())
        bdn.rvComic.setHasFixedSize(true)
        bdn.rvComic.layoutManager = GridLayoutManager(this, 2)

        bdn.btnShowFilterSearch.setOnClickListener {
            startActivity(Intent(this, FilterSearchActivity::class.java))
        }
    }

    private fun preloadImages(comicList: List<MyComic>) {
        for (imageUrl in comicList){
            Picasso.get().load(imageUrl.Image).fetch()
        }
    }


    private fun loadComic() {
        alertDialog.show()
        comicDb.addListenerForSingleValueEvent(object : ValueEventListener {
            var comicLoad: MutableList<MyComic> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    it.getValue(MyComic::class.java)?.let { it1 -> comicLoad.add(it1) }
                }
                iComicLoadDoneListener.onComicLoadDoneListener(comicLoad)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadBanners() {
        bannersDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val banner_list = ArrayList<String>()

                for (banner in snapshot.children) {
                    val image = banner.getValue(String::class.java)
                    image?.let { banner_list.add(it) }
                }
                iBannerLoadDoneListener.onBannerLoadDoneListener(banner_list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBannerLoadDoneListener(banners: List<String>) {
        bdn.slider.setAdapter(MySliderAdapter(banners))
    }

    override fun onComicLoadDoneListener(comicList: List<MyComic>) {
        Common.comicList = comicList

        bdn.rvComic.adapter = ComicAdapter(this, comicList)
        bdn.comicTxt.text = StringBuilder("NEW COMICS (")
            .append(comicList.size)
            .append(")")
        alertDialog.dismiss()

        if (bdn.swipeToRefresh.isRefreshing)
            bdn.swipeToRefresh.isRefreshing = false
    }
}