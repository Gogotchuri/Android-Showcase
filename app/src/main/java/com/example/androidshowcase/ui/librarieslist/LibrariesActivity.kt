package com.example.androidshowcase.ui.librarieslist

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidshowcase.R
import com.example.androidshowcase.database.ShowcaseDatabase
import com.example.androidshowcase.database.entities.Library
import com.example.androidshowcase.databinding.ActivityLibrariesBinding
import com.example.androidshowcase.ui.insidelibrary.LibraryComponentsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LibrariesActivity : AppCompatActivity() {
    private lateinit var adapter: LibrariesRecyclerAdapter
    private lateinit var context: Context
    private lateinit var binding: ActivityLibrariesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibrariesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.context = this

        supportActionBar?.hide()
        val showcaseDatabase = ShowcaseDatabase.getInstance(context)
        GlobalScope.launch {
            val librariesDao = showcaseDatabase.getLibrariesDao()
            val libraries: List<Library> = librariesDao.getAllLibraries()

            runOnUiThread {
                adapter = LibrariesRecyclerAdapter(libraries)
                adapter.itemClickedListener = {libraryName ->
                    LibraryComponentsActivity.start(context, libraryName)
                }
                binding.recyclerViewLibraries.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewLibraries.adapter = adapter
            }

        }
    }
}