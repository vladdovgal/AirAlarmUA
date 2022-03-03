package com.alarmua.ui.locations.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alarmua.databinding.ItemLocationBinding
import com.alarmua.model.LocationItem


class LocationsListAdapter(
    val onClick: (locationId: String) -> Unit,
) : RecyclerView.Adapter<LocationsListAdapter.LocationViewHolder>() {

    private val itemsList: ArrayList<LocationItem> = ArrayList()

    fun submitList(items: List<LocationItem>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = itemsList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = itemsList.size

    inner class LocationViewHolder(private val itemBinding: ItemLocationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(location: LocationItem) {
            with(itemBinding) {
                locationName.setText(location.readableName)
                radioButton.isChecked = location.isSelected
                locationItem.setOnClickListener {
                    radioButton.performClick()
                    onItemClick(location)
                }
                radioButton.setOnClickListener {
                    onItemClick(location)
                }
            }

        }

        private fun onItemClick(location: LocationItem) {
            onClick.invoke(location.id)
        }
    }
}