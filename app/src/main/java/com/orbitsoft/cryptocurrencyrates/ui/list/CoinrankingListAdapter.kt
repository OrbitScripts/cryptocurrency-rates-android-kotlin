package com.orbitsoft.cryptocurrencyrates.ui.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUI

class CoinrankingListAdapter(
    diffCallback: DiffUtil.ItemCallback<ListItem>,
    private val onRetryBtnCallback: () -> Unit
) : PagingDataAdapter<ListItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int) = when (val item = getItem(position)) {
        ListItem.EmptyState -> ITEM_TYPE_EMPTY_STATE
        is ListItem.Row, null -> ITEM_TYPE_COIN
        else -> throw IllegalArgumentException(
            "Item types ${item::javaClass} is not allowed. Pos: $position; count: $itemCount"
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is CoinVH && item is ListItem.Row)
            holder.bind(item.coinUi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_TYPE_COIN -> CoinVH(CoinRowView(parent.context))
        ITEM_TYPE_EMPTY_STATE -> EmptyStateVH(EmptyStateRowView(parent.context, onRetryBtnCallback))
        else -> throw IllegalStateException("ViewType $viewType is not allowed")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is CoinVH)
            holder.recycle()
    }

    class CoinVH(private val coinRowView: CoinRowView) : RecyclerView.ViewHolder(coinRowView) {
        fun bind(coinUi: CoinUI) = coinRowView.bind(coinUi)
        fun recycle() = coinRowView.recycle()
    }

    class EmptyStateVH(
        private val emptyStateRowView: EmptyStateRowView
    ) : RecyclerView.ViewHolder(emptyStateRowView)

    companion object {
        private const val ITEM_TYPE_COIN = 0
        private const val ITEM_TYPE_EMPTY_STATE = 1
    }
}