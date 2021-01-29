package com.example.paintschemetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MiniatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMiniature(miniature: MiniatureEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(miniatures: List<MiniatureEntity>)
    @Query("SELECT * FROM miniatures ORDER BY id ASC")
    fun getAll(): LiveData<List<MiniatureEntity>>

    @Query("SELECT * FROM miniatures WHERE id = :id")
    fun getMiniById(id: Int): MiniatureEntity?

    @Query("SELECT COUNT(*) FROM miniatures")
    fun getCount(): Int

    @Delete
    fun deleteMinis(selectedMiniatures: List<MiniatureEntity>): Int

            @Query("DELETE FROM miniatures")
            fun deleteAll():Int

            @Delete
            fun deleteMiniature(miniature: MiniatureEntity)


}