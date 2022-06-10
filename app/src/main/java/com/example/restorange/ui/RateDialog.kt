package com.example.restorange.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.restorange.R
import com.example.restorange.db.entity.PlaceEntity
import java.lang.ClassCastException
import java.util.*

class RateDialog : DialogFragment() {

    interface RateDialogListener {
        fun onDialogResult(place: PlaceEntity,number: Int)
    }

    private var number: Int = 1
    private var id: Long = 0
    private var place = PlaceEntity(
        id,
        "",
        "",
        number,
        Date(),
        Date()
    )
    private val list = arrayOf("1","2","3","4","5","6","7","8","9","10")
    private lateinit var listener: RateDialogListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number = arguments?.getInt("number") as Int
        id = arguments?.getLong("id") as Long
        place = arguments?.getParcelable<PlaceEntity>("place") as PlaceEntity
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Rate this place")
            .setSingleChoiceItems(list,number-1) { _,i -> number = i+1}
            .setIcon(R.drawable.star_rate)
            .setPositiveButton("Ok"){_,i -> listener.onDialogResult(place,number)}
            .setNegativeButton("Cancel",null)
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as RateDialogListener
        }catch (e: ClassCastException){

        }
    }
    companion object {
        fun getNumber(place: PlaceEntity): RateDialog {
            val args = Bundle()
            args.putInt("number",place.rating)
            args.putLong("id",place.id)
            args.putParcelable("place",place)
            val fragment = RateDialog()
            fragment.arguments = args
            return fragment
        }
    }
}