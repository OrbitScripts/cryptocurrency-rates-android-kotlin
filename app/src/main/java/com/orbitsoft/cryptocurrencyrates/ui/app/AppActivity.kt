package com.orbitsoft.cryptocurrencyrates.ui.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orbitsoft.cryptocurrencyrates.R
import com.orbitsoft.cryptocurrencyrates.databinding.ActivityAppBinding
import com.orbitsoft.cryptocurrencyrates.ui.list.CoinrankingListAdapter
import com.orbitsoft.cryptocurrencyrates.ui.list.ListItem
import com.orbitsoft.cryptocurrencyrates.ui.list.ListItemDiffUtilCallback
import com.orbitsoft.cryptocurrencyrates.ui.list.placeholder.PlaceholdersListAdapter
import com.orbitsoft.cryptocurrencyrates.ui.list.state.StateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@ExperimentalPagingApi
@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private val viewModel: AppActivityViewModel by viewModels()
    private val viewBinding: ActivityAppBinding by viewBinding()
    private var observePagingDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initList()
        observePagingDataFlow()
        observeOrderState()
        viewBinding.header.onChangeOrderCallback = { order -> viewModel.onOrderChange(order) }
    }

    private fun initList() {
        with(viewBinding.list) {
            layoutManager = LinearLayoutManager(this@AppActivity)
            setHasFixedSize(true)
            ContextCompat.getDrawable(this@AppActivity, R.drawable.row_divider)?.let { drawable ->
                addItemDecoration(
                    DividerItemDecoration(
                        this@AppActivity,
                        LinearLayoutManager.VERTICAL
                    ).apply {
                        setDrawable(drawable)
                    }
                )
            }

            val adapter = CoinrankingListAdapter(ListItemDiffUtilCallback()) { reload() }
            val placeholdersAdapter = PlaceholdersListAdapter()
            val stateAdapter = StateAdapter() { adapter.retry() }

            setAdapter(ConcatAdapter(placeholdersAdapter, adapter, stateAdapter))
            //Add listener to catch empty state exception and show empty state row
            adapter.addLoadStateListener { loadStates ->
                //Work only with append and refresh because we don't have prepend
                val append = loadStates.append
                val refresh = loadStates.refresh

                val isAppendLoadingOrError =
                    append is LoadState.Loading || append is LoadState.Error
                val isInitialLoading =
                    (append is LoadState.Loading || refresh is LoadState.Loading) &&
                            adapter.itemCount == 0

                stateAdapter.loadState =
                    if (!isInitialLoading && isAppendLoadingOrError) {
                        append //If not init loading show loading state on footer
                    } else {
                        LoadState.NotLoading(true) //disable loading state on footer
                    }

                //If init loading then enable placeholders
                placeholdersAdapter.enabled = isInitialLoading

                val isNeedShowEmptyState =
                    (append is LoadState.NotLoading &&
                            append.endOfPaginationReached) ||
                            (refresh is LoadState.Error ||
                                    (refresh is LoadState.NotLoading &&
                                            refresh.endOfPaginationReached)) &&
                            adapter.itemCount == 0

                //Show empty state row
                if (isNeedShowEmptyState) {
                    adapter.submitData(lifecycle, viewModel.getEmptyStatePagingData())
                }
            }
        }
    }

    private fun reload() = viewModel.reload()

    private fun observeOrderState() = lifecycleScope.launchWhenStarted {
        viewModel.orderState.collect { order ->
            viewBinding.header.order = order
        }
    }

    private fun observePagingDataFlow() = lifecycleScope.launchWhenStarted {
        viewModel.pagingDataFlowState.collect { pagingDataFlow ->
            pagingDataFlow?.let {
                observePagingDataJob?.cancel()
                observePagingDataJob?.join()
                observePagingDataJob = observePagingData(pagingDataFlow)
            }
        }
    }

    private fun observePagingData(pagingDataFlow: Flow<PagingData<ListItem>>) =
        lifecycleScope.launchWhenStarted {
            pagingDataFlow
                .collect { pagingData ->
                    //Caution: The submitData() method suspends and does not return until
                    //either the PagingSource is invalidated or the adapter's refresh method is called.
                    //This means that code after the submitData() call might execute much later than you intend.
                    selectCoinrankingListAdapter()?.submitData(
                        lifecycle = lifecycle,
                        pagingData = pagingData
                    )
                }
        }

    private fun selectCoinrankingListAdapter(): CoinrankingListAdapter? =
        when (val adapter = viewBinding.list.adapter) {
            is ConcatAdapter -> adapter.adapters.firstOrNull {
                it is CoinrankingListAdapter
            }?.let { it as CoinrankingListAdapter }

            is CoinrankingListAdapter -> adapter
            else -> null
        }
}