package com.example.androidshowcase.ui.librarieslist

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidshowcase.database.ShowcaseDatabase
import com.example.androidshowcase.database.entities.Library
import com.example.androidshowcase.database.entities.LibraryMarking
import com.example.androidshowcase.database.entities.MarkingType
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

        supportActionBar?.title = "Libraries"
        val showcaseDatabase = ShowcaseDatabase.getInstance(context)
        GlobalScope.launch {
            val librariesDao = showcaseDatabase.getLibrariesDao()
            val libraryMarkingDao = showcaseDatabase.getLibraryMarkingsDao()
            val markingTypesDao = showcaseDatabase.getMarkingTypesDao()

            val pinnedLibraries: List<Library> = librariesDao.getAllPinnedLibraries()
            val notPinnedLibraries: List<Library> = librariesDao.getAllNotPinnedLibraries()
            val libraries : ArrayList<Library> = arrayListOf()
            pinnedLibraries.forEach { library -> libraries.add(library) }
            notPinnedLibraries.forEach { library -> libraries.add(library) }
            val libraryMarkings: List<LibraryMarking> = libraryMarkingDao.getAllLibraryMarkings()
            val markingTypes: List<MarkingType> = markingTypesDao.getAllMarkingTypes()
            runOnUiThread {
                adapter = LibrariesRecyclerAdapter(libraries, libraryMarkings, markingTypes)
                adapter.itemClickedListener = { libraryName ->
                    LibraryComponentsActivity.start(context, libraryName)
                }
                adapter.onCheckedListener = { button, isChecked ->
                    GlobalScope.launch {
                        val libraryName = button.tag as String
                        val library = librariesDao.getLibraryByName(libraryName)
                        val pinMarking = markingTypesDao.getMarkingTypeByName("pin")
                        if (isChecked) {
                            libraryMarkingDao.insertLibraryMarking(LibraryMarking(0, library.id, pinMarking.id))
                        } else {
                            val libraryMarking = libraryMarkingDao.getLibraryMarkingByLibraryId(library.id)
                            libraryMarkingDao.deleteLibraryMarking(libraryMarking)
                        }
                        button.tag = null
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                }
                binding.recyclerViewLibraries.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewLibraries.adapter = adapter
            }
        }
    }
}