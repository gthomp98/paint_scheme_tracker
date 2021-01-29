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

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val minisList = database?.miniatureDao()?.getAll()

    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleMinis = SampleDataProvider.getMiniatures()
                database?.miniatureDao()?.insertAll(sampleMinis)
            }
        }

    }

    fun deleteMinis(selectedMiniatures: List<MiniatureEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.miniatureDao()?.deleteMinis(selectedMiniatures)
            }
        }
    }

    fun deleteAllMiniatures() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.miniatureDao()?.deleteAll()
            }
        }
    }
}