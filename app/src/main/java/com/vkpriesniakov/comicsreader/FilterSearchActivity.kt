package com.vkpriesniakov.comicsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.vkpriesniakov.comicsreader.adapter.ComicAdapter
import com.vkpriesniakov.comicsreader.common.Common
import com.vkpriesniakov.comicsreader.databinding.ActivityFilterSearchBinding
import com.vkpriesniakov.comicsreader.databinding.ChipItemBinding
import com.vkpriesniakov.comicsreader.databinding.DialogFilterBinding
import com.vkpriesniakov.comicsreader.databinding.DialogSearchBinding
import com.vkpriesniakov.comicsreader.model.MyComic
import java.util.*
import kotlin.collections.ArrayList

class FilterSearchActivity : AppCompatActivity() {

    private lateinit var bdn: ActivityFilterSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bdn = ActivityFilterSearchBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)

        bdn.bottomAppBar.inflateMenu(R.menu.my_menu)
        bdn.bottomAppBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_filter -> showOptionsDialog()
                R.id.action_search -> showSearchDialog()
            }
            true
        }
        bdn.recyclerFilterSearch.setHasFixedSize(true)
        bdn.recyclerFilterSearch.layoutManager = GridLayoutManager(this, 2)
    }

    private fun showSearchDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Select Category")

        val mInflater = this.layoutInflater
        val bdnSearch = DialogSearchBinding.inflate(mInflater)
        alertDialog.setView(bdnSearch.root)
        alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        alertDialog.setPositiveButton("Search") { _, _ ->
            fetchSearchComics(bdnSearch.editTextSearch.text.toString())
        }
        alertDialog.show()

    }

    private fun fetchSearchComics(search: String) {
        val searchedComics = ArrayList<MyComic>()
        Common.comicList.forEach{
            if (it.Name != null)
                if (it.Name!!.contains(search))
                    searchedComics.add(it)
        }
        if (searchedComics.size>0){
            bdn.recyclerFilterSearch.adapter = ComicAdapter(this, searchedComics)
        } else {
            Snackbar.make(bdn.root, "No results", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showOptionsDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Select Category")

        val mInflater = this.layoutInflater
        val bdnDialog = DialogFilterBinding.inflate(mInflater)
        bdnDialog.editTextCategoryFilter.threshold = 3
        bdnDialog.editTextCategoryFilter.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.select_dialog_item,
                Common.categories
            )
        )
        bdnDialog.editTextCategoryFilter.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                bdnDialog.editTextCategoryFilter.setText("")
                val bdnChip = ChipItemBinding.inflate(mInflater)
                bdnChip.chipItem.text = (view as TextView).text
                bdnChip.chipItem.setOnCloseIconClickListener { view ->
                    bdnDialog.filterChipGroup.removeView(
                        view
                    )
                }
                bdnDialog.filterChipGroup.addView(bdnChip.chipItem)
            }

        alertDialog.setView(bdnDialog.root)
        alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        alertDialog.setPositiveButton("Filter") { dialog, _ ->
            val filterKey = ArrayList<String>()
            val filterQuery = StringBuilder("")
            for (i in 0 until bdnDialog.filterChipGroup.childCount) {
                val chip = bdnDialog.filterChipGroup.getChildAt(i) as Chip
                filterKey.add(chip.text.toString())
            }
            filterKey.sort()
            filterKey.forEach {
                filterQuery.append(it).append(",")
                filterQuery.setLength(filterQuery.length - 1)
                fetchFilterCategory(filterQuery.toString())
            }
        }
        alertDialog.show()
    }

    private fun fetchFilterCategory(query: String) {

        val filteredComics = ArrayList<MyComic>()
        Common.comicList.forEach {
            if (it.Category != null) {
                if (it.Category!!.contains(query)) {
                    filteredComics.add(it)
                }
            }
        }

        if (filteredComics.size > 0) {
            bdn.recyclerFilterSearch.adapter = ComicAdapter(this, filteredComics)
        } else {
            Snackbar.make(bdn.root, "No results", Snackbar.LENGTH_SHORT).show()
        }
    }
}