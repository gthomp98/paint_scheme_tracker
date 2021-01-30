package com.example.paintschemetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
//This is the data access object, it is used to interface with the database to send querys and sql commands to manipulate the data within the database
@Dao
interface MiniatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMiniature(miniature: MiniatureEntity)
//This is the insert command, it is used to insert new data in the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(miniatures: List<MiniatureEntity>)
    //This is the insert all function used for inserting all items into the list
    @Query("SELECT * FROM miniatures ORDER BY id ASC")
    fun getAll(): LiveData<List<MiniatureEntity>>
    //This is the get all function, which gets all miniatures
    @Query("SELECT * FROM miniatures WHERE id = :id")
    fun getMiniById(id: Int): MiniatureEntity?
    //This is the get mini by id function which sorts miniatures by their id
    @Query("SELECT COUNT(*) FROM miniatures")
    fun getCount(): Int
    //this is the get count function, which returns the ammount of rows of data in the miniature table
    @Delete
    fun deleteMinis(selectedMiniatures: List<MiniatureEntity>): Int
    //this is the delete minis function which deletes miniatures by selected
    @Query("DELETE FROM miniatures")
    fun deleteAll():Int
    //this deletes all miniatures from the table
    @Delete
    fun deleteMiniature(miniature: MiniatureEntity)
    //this deletes a miniature from the entity itself

}