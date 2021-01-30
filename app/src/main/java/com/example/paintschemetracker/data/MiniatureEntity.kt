package com.example.paintschemetracker.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//this is the miniature entity, which is reflective of the databases properties
//the parcelize plugin generates code to allow the entity to be parcelable, which means its instance can be written and restored by the state
@Parcelize
@Entity(tableName = "Miniatures")
data class MiniatureEntity (
    //the primary key is generated as true and the id is auto incremented, all fields are strings
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var usedPaints: String,
    var techniques: String,
    var paintMethod: String,
    var modelUsed: String



    ): Parcelable {

//these are the two constructors, one for when an object is created, where an id is auto assigned, and another for when an object that has already been assigned an id is referenced
    constructor(): this(NEW_MINIATURE_ID, "", "", "", "", "")
    constructor(name: String,usedPaints: String, techniques: String, paintMethod: String, modelUsed: String): this(NEW_MINIATURE_ID, name, usedPaints, techniques, paintMethod, modelUsed)
}


