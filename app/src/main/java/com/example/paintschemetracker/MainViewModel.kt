package com.example.paintschemetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.paintschemetracker.data.AppDatabase
import com.example.paintschemetracker.data.MiniatureEntity
import com.example.paintschemetracker.data.SampleDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//This is the main view model, this is what represents the mains data view. These are functions that are used in the main by way of the item menu
class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val minisList = database?.miniatureDao()?.getAll()

    //this function goes to the dao and inserts 3 generated objects into the database
    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleMinis = SampleDataProvider.getMiniatures()
                database?.miniatureDao()?.insertAll(sampleMinis)
            }
        }

    }
//this deletes the selected miniatures from the list, by going to the dao, selecting the selected miniatures id within the table and deleting them
    fun deleteMinis(selectedMiniatures: List<MiniatureEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.miniatureDao()?.deleteMinis(selectedMiniatures)
            }
        }
    }
//this deletes all miniatures within the table, completely clearing it
    fun deleteAllMiniatures() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.miniatureDao()?.deleteAll()
            }
        }
    }
}