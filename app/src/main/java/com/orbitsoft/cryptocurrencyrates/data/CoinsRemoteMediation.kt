package com.orbitsoft.cryptocurrencyrates.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.orbitsoft.cryptocurrencyrates.data.source.local.LocalDataSource
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.RemoteOffsetEntity
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingSource
import com.orbitsoft.cryptocurrencyrates.data.source.remote.model.asListOfCoinEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class CoinsRemoteMediation(
    private val remote: CoinrankingSource,
    private val local: LocalDataSource,
    private val order: Order
) : RemoteMediator<Int, CoinEntity>() {

    companion object {
        const val CACHE_TTL = 3600000 //1 hour
    }

    override suspend fun initialize(): InitializeAction = withContext(Dispatchers.IO) {
        val addedAt = local.getAddedAtOrder(order)
        return@withContext if (System.currentTimeMillis() - addedAt <= CACHE_TTL) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network;
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {
        // The network load method takes offset parameter. For every page
        // after the first, pass the offset returned from the previous page to
        // let it continue from where it left off. For REFRESH, pass 0 to load the
        // first page.
        val remoteOffset = when (loadType) {
            LoadType.REFRESH -> RemoteOffsetEntity(0, order)

            LoadType.PREPEND -> return@withContext MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {
                // null is only valid for initial load when appending. If we receive null
                // for APPEND, that means we have reached the end of pagination
                val remoteOffset = local.remoteOffsetByOrder(order)
                    ?: return@withContext MediatorResult.Success(endOfPaginationReached = true)
                remoteOffset
            }
        }


        val limit: Int =
            if (remoteOffset.nextOffset == 0) state.config.initialLoadSize else state.config.pageSize
        val dataResult = remote.loadCoins(
            offset = remoteOffset.nextOffset,
            limit = limit,
            order = order
        )

        return@withContext when (dataResult) {
            is DataResult.Error ->
                MediatorResult.Error(dataResult.throwable)


            is DataResult.Success -> {
                val nextOffset = remoteOffset.nextOffset + dataResult.data.coins.size

                // Insert new rows into database, which invalidates the current
                // PagingData, allowing Paging to present the updates in the DB.
                local.insertCoins(
                    coins = dataResult.data.coins.asListOfCoinEntity(
                        order = order,
                        startPosition = remoteOffset.nextOffset.toLong(),
                        System.currentTimeMillis()
                    ),
                    nextOffset = RemoteOffsetEntity(
                        nextOffset = nextOffset,
                        order = order
                    ),
                    withClearCache = loadType == LoadType.REFRESH
                )
                MediatorResult.Success(
                    dataResult.data.coins.isEmpty() ||
                            nextOffset >= dataResult.data.stats.total
                )
            }
        }
    }
}