package com.example.restorange.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "places")
@Parcelize
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val address: String,
    val rating: Int,
    @ColumnInfo(name = "date_creation") val creationDate: Date,
    @ColumnInfo(name = "date_update")val updateDate: Date
): Parcelable