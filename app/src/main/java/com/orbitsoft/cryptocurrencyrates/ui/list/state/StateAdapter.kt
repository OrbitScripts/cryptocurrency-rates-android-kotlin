package com.orbitsoft.cryptocurrencyrates.ui.list.state

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(
    private val onRetryBtnCallback: () -> Unit
) : LoadStateAdapter<StateAdapter.StateVH>() {

    override fun onBindViewHolder(holder: StateVH, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        StateVH(StateRowView(parent.context, onRetryBtnCallback))

    class StateVH(
        private val stateRowView: StateRowView
    ) : RecyclerView.ViewHolder(stateRowView) {
        fun bind(loadState: LoadState) {
            stateRowView.bind(loadState)
        }
    }
}