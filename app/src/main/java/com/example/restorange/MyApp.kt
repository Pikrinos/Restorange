package com.example.restorange

import android.app.Application
import com.example.restorange.db.PlaceRoomDatabase

class MyApp: Application() {

    val database by lazy { PlaceRoomDatabase.getInstance(this) }
    val repository by lazy {PlaceRepository(database.placeDao())}}