package com.alarmua.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alarmua.databinding.ItemDashboardLinkBinding
import com.alarmua.ui.locations.list.DashboardItem

class DashboardLinksAdapter(
    val onClick: (linkURL: String) -> Unit,
) : RecyclerView.Adapter<DashboardLinksAdapter.LinkViewHolder>() {

    private val itemsList: ArrayList<DashboardItem> = ArrayList()

    fun submitList(items: List<DashboardItem>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val itemBinding = ItemDashboardLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemsList.size

    inner class LinkViewHolder(private val itemBinding: ItemDashboardLinkBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DashboardItem) {
            with(itemBinding) {
                linkImage.setImageResource(item.iconRes)
                linkTitle.setText(item.titleRes)
                root.setOnClickListener {
                    onClick.invoke(item.url)
                }
            }

        }
    }
}