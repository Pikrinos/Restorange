package com.example.restorange.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.restorange.databinding.ActivityEditPlaceBinding
import com.example.restorange.db.entity.PlaceEntity
import java.util.*


class EditPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityEditPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val oldPlace = intent.getParcelableExtra<PlaceEntity>(Contract.EXTRA_PLACE)
        binding.name.setText(oldPlace?.name ?: "")
        binding.address.setText(oldPlace?.address ?: "")
        when(oldPlace?.rating){
            null ->  binding.radioGroup.check(0)
            else ->  binding.radioGroup.check(oldPlace.rating)
        }
        var rId = oldPlace?.rating ?: 0
        val radioGroup = binding.radioGroup
        var buttonId = 0
        when(oldPlace?.rating){
            1 -> buttonId = binding.radioButton1.id
            2 -> buttonId = binding.radioButton2.id
            3 -> buttonId = binding.radioButton3.id
            4 -> buttonId = binding.radioButton4.id
            5 -> buttonId = binding.radioButton5.id
            6 -> buttonId = binding.radioButton6.id
            7 -> buttonId = binding.radioButton7.id
            8 -> buttonId = binding.radioButton8.id
            9 -> buttonId = binding.radioButton9.id
            10 -> buttonId = binding.radioButton10.id
        }
        radioGroup.check(buttonId)
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                binding.radioButton1.id -> rId = 1
                binding.radioButton2.id -> rId = 2
                binding.radioButton3.id -> rId = 3
                binding.radioButton4.id -> rId = 4
                binding.radioButton5.id -> rId = 5
                binding.radioButton6.id -> rId = 6
                binding.radioButton7.id -> rId = 7
                binding.radioButton8.id -> rId = 8
                binding.radioButton9.id -> rId = 9
                binding.radioButton10.id -> rId = 10
                else -> rId = oldPlace?.rating ?: 0
            }
        }

        binding.add.setOnClickListener {
            val i = Intent()

            val id = oldPlace?.id ?: 0
            val creationDate = oldPlace?.creationDate ?: Date()
            val updateDate = if(oldPlace == null) creationDate else Date()

            val place = PlaceEntity(
                id,
                binding.name.text.toString(),
                binding.address.text.toString(),
                rId,
                creationDate,
                updateDate
            )
            i.putExtra(Contract.EXTRA_PLACE,place)
            setResult(RESULT_OK,i)
            finish()
        }

        binding.cancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    class Contract : ActivityResultContract<Contract.Input, Contract.Output>() {
        data class Input(val place: PlaceEntity?)
        data class Output(val place: PlaceEntity?, val result: Boolean)

        override fun createIntent(context: Context, input: Input): Intent {
            val intent = Intent(context, EditPlaceActivity::class.java)
            intent.putExtra(EXTRA_PLACE, input.place)
            return intent
        }
        override fun parseResult(resultCode: Int,intent: Intent?): Output {
            val place = intent?.getParcelableExtra<PlaceEntity>(EXTRA_PLACE)
            return Output(place, resultCode == RESULT_OK)
        }

        companion object{
            const val EXTRA_PLACE = "EXTRA_PLACE"
        }
    }
}