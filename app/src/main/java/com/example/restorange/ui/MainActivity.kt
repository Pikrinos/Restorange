package com.example.restorange.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restorange.MyApp
import com.example.restorange.databinding.ActivityMainBinding
import com.example.restorange.db.entity.PlaceEntity
import com.example.restorange.viewmodel.PlaceViewModel
import com.example.restorange.viewmodel.PlaceViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity(),RateDialog.RateDialogListener,DeleteDialog.DeleteDialogListener {

    private lateinit var binding: ActivityMainBinding
    private val createPlaceLauncher = registerForActivityResult(EditPlaceActivity.Contract()){
        when (it.result){
            true -> it.place?.let { place -> placeViewModel.insert(place)}
            else -> Unit
        }
    }

    private val editPlaceLauncher = registerForActivityResult(EditPlaceActivity.Contract()){
        when (it.result){
            true -> it.place?.let { place -> placeViewModel.update(place)}
            else -> Unit
        }
    }

    private val placeViewModel: PlaceViewModel by viewModels {
        PlaceViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycler = binding.list
        val adapter = PlacesAdapter(layoutInflater,
            { editPlaceLauncher.launch(EditPlaceActivity.Contract.Input(it)) },
            { RateDialog.getNumber(it).show(supportFragmentManager,"dlg") },
            { DeleteDialog.getName(it).show(supportFragmentManager,"dlg") }
        )
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        placeViewModel.allPlaces.observe(this) {
            adapter.submitList(it)
        }

        binding.fub.setOnClickListener {
            createPlaceLauncher.launch(EditPlaceActivity.Contract.Input(null))
        }

    }

    override fun onDialogResult(place: PlaceEntity,number: Int) {
        placeViewModel.delete(place)
        val id = place.id
        val creationDate = place.creationDate
        val updateDate = Date()

        val place = PlaceEntity(
            id,
            place.name,
            place.address,
            number,
            creationDate,
            updateDate
        )
        placeViewModel.insert(place)
    }

    override fun onDialogResultDelete(place: PlaceEntity) {
        placeViewModel.delete(place)
    }
}