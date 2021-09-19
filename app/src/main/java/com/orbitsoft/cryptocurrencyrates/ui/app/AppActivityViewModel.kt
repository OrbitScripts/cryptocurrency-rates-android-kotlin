package com.orbitsoft.cryptocurrencyrates.ui.app

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.orbitsoft.cryptocurrencyrates.data.CoinsRemoteMediation
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.source.local.LocalDataSource
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingSource
import com.orbitsoft.cryptocurrencyrates.ui.list.ListItem
import com.orbitsoft.cryptocurrencyrates.ui.model.asCoinUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AppActivityViewModel @Inject constructor(
    private val remote: CoinrankingSource,
    private val local: LocalDataSource
) : ViewModel() {

    private val _orderState: MutableStateFlow<Order> = MutableStateFlow(Order())
    val orderState = _orderState.asStateFlow()
    private val _pagingDataFlowState: MutableStateFlow<Flow<PagingData<ListItem>>?> =
        MutableStateFlow(null)
    val pagingDataFlowState = _pagingDataFlowState.asStateFlow()

    init {
        initPagingDataFlowState(orderState.value)
    }

    private fun initPagingDataFlowState(order: Order) {
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        )
        _pagingDataFlowState.value = Pager(
            config = config,
            initialKey = null,
            remoteMediator = CoinsRemoteMediation(
                remote = remote,
                local = local,
                order = order
            ),
            pagingSourceFactory = { local.load(order) }
        )
            .flow
            .map { pagingData ->
                pagingData.map { coinEntity -> ListItem.Row(coinEntity.asCoinUI()) }
            }
    }

    /**
     * Set new order
     *
     * @param newOrder new order
     */
    fun onOrderChange(newOrder: Order) {
        _orderState.value = newOrder
        initPagingDataFlowState(newOrder)
    }

    /**
     * Generate paging data with empty state
     *
     * @return Return paging data with empty state
     */
    fun getEmptyStatePagingData(): PagingData<ListItem> =
        PagingData.from(listOf(ListItem.EmptyState))

    fun reload() = onOrderChange(orderState.value)

    companion object {
        const val PAGE_SIZE = 30
        const val INITIAL_LOAD_SIZE = 50
        const val PREFETCH_DISTANCE = 10
    }
}