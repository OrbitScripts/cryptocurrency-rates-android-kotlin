package com.orbitsoft.cryptocurrencyrates.ui.list

import androidx.recyclerview.widget.DiffUtil

class ListItemDiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =
        if (oldItem is ListItem.Row && newItem is ListItem.Row)
            oldItem.coinUi.localUuid == newItem.coinUi.localUuid
        else false

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
        if (oldItem is ListItem.Row && newItem is ListItem.Row)
            oldItem.coinUi == newItem.coinUi
        else false
}