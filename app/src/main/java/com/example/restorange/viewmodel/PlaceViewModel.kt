package com.example.restorange.viewmodel

import androidx.lifecycle.*
import com.example.restorange.PlaceRepository
import com.example.restorange.db.entity.PlaceEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {

    val allPlaces: LiveData<List<PlaceEntity>> = repository.allPlaces.asLiveData()

    fun insert(place: PlaceEntity) = viewModelScope.launch{
        repository.insert(place)
    }

    fun update(place: PlaceEntity) = viewModelScope.launch{
        repository.update(place)
    }

    fun delete(place: PlaceEntity) = viewModelScope.launch{
        repository.delete(place)
    }

    fun getByID(id: Long) = viewModelScope.launch{
        repository.getById(id)
    }
}

class PlaceViewModelFactory(private val repository: PlaceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlaceViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PlaceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}