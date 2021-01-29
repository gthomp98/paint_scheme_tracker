package com.example.paintschemetracker.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Miniatures")
data class MiniatureEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var usedPaints: String,
    var techniques: String,
    var paintMethod: String,
    var modelUsed: String



    ): Parcelable {


    constructor(): this(NEW_MINIATURE_ID, "", "", "", "", "")
    constructor(name: String,usedPaints: String, techniques: String, paintMethod: String, modelUsed: String): this(NEW_MINIATURE_ID, name, usedPaints, techniques, paintMethod, modelUsed)
}


