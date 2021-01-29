package com.example.paintschemetracker


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paintschemetracker.data.AppDatabase
import com.example.paintschemetracker.data.MiniatureEntity
import com.example.paintschemetracker.data.NEW_MINIATURE_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditorViewModel(app: Application): AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val currentMiniature = MutableLiveData<MiniatureEntity>()

    fun getMiniatureById(miniatureId: Int){
    viewModelScope.launch{

        withContext(Dispatchers.IO) {
            val miniature =
                if (miniatureId != NEW_MINIATURE_ID) {
                    database?.miniatureDao()?.getMiniById(miniatureId)
                }else {
                    MiniatureEntity()
                }
            currentMiniature.postValue(miniature)

            }
        }
    }

    fun updateMiniature() {
        currentMiniature.value?.let {
            it.name = it.name.trim()
            if(it.id == NEW_MINIATURE_ID &&it.name.isEmpty()) {
                return
            }

            viewModelScope.launch{
                withContext(Dispatchers.IO) {
                    if (it.name.isEmpty()) {
                        database?.miniatureDao()?.deleteMiniature(it)
                    }else {
                        database?.miniatureDao()?.insertMiniature(it)
                    }
                }
            }
        }
    }

}


