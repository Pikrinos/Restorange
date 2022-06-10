package com.example.restorange.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.example.restorange.db.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places ORDER BY rating DESC")
    fun getAllPlaces() : Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places ORDER BY date_update DESC")
    fun getAllPlacesOrdered() : Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE id = :id_arg")
    suspend fun getById(id_arg: Long) : PlaceEntity

    @Insert(onConflict = IGNORE)
    suspend fun insert(place: PlaceEntity)

    @Delete
    suspend fun delete(place: PlaceEntity)

    @Update
    suspend fun update(place: PlaceEntity)
}