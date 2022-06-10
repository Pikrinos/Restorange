package com.example.restorange.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restorange.R
import com.example.restorange.db.entity.PlaceEntity


class PlacesAdapter(
    private val inflater: LayoutInflater,
    private val onClick:(PlaceEntity) -> Unit,
    private val onRate:(PlaceEntity) -> Unit,
    private val onDelete:(PlaceEntity) -> Unit,
): ListAdapter<PlaceEntity, PlacesAdapter.ViewHolder>(PlaceDiffCallback) {


    class ViewHolder(view: View,val onClick:(PlaceEntity) -> Unit,val onRate:(PlaceEntity) -> Unit,val onDelete:(PlaceEntity) -> Unit) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.p_name)
        private val address = view.findViewById<TextView>(R.id.p_address)
        private val rating = view.findViewById<TextView>(R.id.p_rating)
        private val delete = view.findViewById<ImageView>(R.id.delete)
        private var place: PlaceEntity? = null

        init {
            view.setOnClickListener {
                place?.let{
                    onClick(it)
                }
            }
        }
        init {
            rating.setOnClickListener {
                place?.let{
                    onRate(it)
                }
            }
        }
        init {
            delete.setOnClickListener {
                place?.let{
                    onDelete(it)
                }
            }
        }
        fun bind(place: PlaceEntity){
            this.place = place
            name.text = place.name
            address.text = place.address
            rating.text = place.rating.toString() + "/10"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item,parent,false)

        return ViewHolder(view,onClick,onRate,onDelete)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }

    object PlaceDiffCallback : DiffUtil.ItemCallback<PlaceEntity>(){
        override fun areItemsTheSame(
            oldItem: PlaceEntity,
            newItem: PlaceEntity
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: PlaceEntity,
            newItem: PlaceEntity
        ): Boolean = oldItem.name == newItem.name && oldItem.address == newItem.address

    }
}