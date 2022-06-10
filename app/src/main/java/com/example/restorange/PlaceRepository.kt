package com.example.restorange

import androidx.annotation.WorkerThread
import com.example.restorange.db.dao.PlaceDao
import com.example.restorange.db.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

class PlaceRepository(private val placeDao: PlaceDao) {

    val allPlaces: Flow<List<PlaceEntity>> = placeDao.getAllPlaces()

    @WorkerThread
    suspend fun insert(place: PlaceEntity){
        placeDao.insert(place)
    }
    @WorkerThread
    suspend fun delete(place: PlaceEntity){
        placeDao.delete(place)
    }
    @WorkerThread
    suspend fun update(place: PlaceEntity){
        placeDao.update(place)
    }
    @WorkerThread
    suspend fun getById(id: Long){
        placeDao.getById(id)
    }
}