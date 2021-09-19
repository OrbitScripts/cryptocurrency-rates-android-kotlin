package com.orbitsoft.cryptocurrencyrates.ui.list.placeholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlaceholdersListAdapter : RecyclerView.Adapter<PlaceholdersListAdapter.PlaceholderVH>() {

    private var count = 0
    var enabled: Boolean = false
        set(value) {
            if (value && count == 0) {
                count = 20
                notifyDataSetChanged()
            }
            if (!value && count > 0) {
                count = 0
                notifyDataSetChanged()
            }
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaceholderVH(
        PlaceholderRowView(parent.context)
    )

    override fun onBindViewHolder(holder: PlaceholderVH, position: Int) = holder.bind()

    override fun getItemCount(): Int = count

    override fun onViewRecycled(holder: PlaceholderVH) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    class PlaceholderVH(
        private val placeholderRowView: PlaceholderRowView
    ) : RecyclerView.ViewHolder(placeholderRowView) {

        fun bind() = placeholderRowView.bind()

        fun recycle() = placeholderRowView.recycle()
    }
}