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

class DeleteDialog : DialogFragment() {

    interface DeleteDialogListener {
        fun onDialogResultDelete(place: PlaceEntity)
    }

    private var place = PlaceEntity(
        0,
        "",
        "",
        1,
        Date(),
        Date()
    )

    private lateinit var listener: DeleteDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        place = arguments?.getParcelable<PlaceEntity>("place") as PlaceEntity
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setIcon(R.drawable.delete_button)
            .setMessage("Are you sure you want to delete ${place.name} ?")
            .setPositiveButton("Ok"){_,i -> listener.onDialogResultDelete(place)}
            .setNegativeButton("No",null)
            .create()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as DeleteDialogListener
        }catch (e: ClassCastException){

        }
    }
    companion object {
        fun getName(place: PlaceEntity): DeleteDialog {
            val args = Bundle()
            args.putParcelable("place",place)
            val fragment = DeleteDialog()
            fragment.arguments = args
            return fragment
        }
    }
}