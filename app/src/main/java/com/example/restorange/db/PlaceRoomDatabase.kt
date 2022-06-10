package com.example.restorange.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.restorange.db.converter.DateConverter
import com.example.restorange.db.dao.PlaceDao
import com.example.restorange.db.entity.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class PlaceRoomDatabase : RoomDatabase() {

    abstract fun placeDao() : PlaceDao

    companion object{
        private var INSTANCE: PlaceRoomDatabase? = null

        fun getInstance(context: Context): PlaceRoomDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceRoomDatabase::class.java,
                    "place_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}