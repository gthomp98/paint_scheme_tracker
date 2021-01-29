package com.example.paintschemetracker

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paintschemetracker.data.AppDatabase
import com.example.paintschemetracker.data.MiniatureDao
import com.example.paintschemetracker.data.SampleDataProvider
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: MiniatureDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.miniatureDao()!!
    }

    @Test
    fun createMiniatures() {
        // Context of the app under test.
        dao.insertAll(SampleDataProvider.getMiniatures())
        val count = dao.getCount()
        assertEquals(count, SampleDataProvider.getMiniatures().size)
    }

    @After

    fun closeDb() {
        database.close()
    }
}