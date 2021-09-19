package com.orbitsoft.cryptocurrencyrates.data.source.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.db.AppDatabase
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.RemoteOffsetEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val db: AppDatabase
) {
    private val coinsDao = db.coinsDao()

    /**
     * Retrieve remote offset for order
     *
     * @param order
     * @return
     */
    suspend fun remoteOffsetByOrder(order: Order): RemoteOffsetEntity? {
        return try {
            coinsDao.remoteOffsetByOrder(order)
        } catch (e: Exception) {
            RemoteOffsetEntity(0, order)
        }
    }


    /**
     * Insert list of coins to cache.Cache will be cleared before inserting if needed.
     *
     * @param coins          list of coins
     * @param nextOffset     next remote offset
     * @param withClearCache to trigger cache clear before inserting
     */
    suspend fun insertCoins(
        coins: List<CoinEntity>,
        nextOffset: RemoteOffsetEntity,
        withClearCache: Boolean
    ) {
        if (coins.isEmpty()) return
        val order = coins[0].order
        db.withTransaction {
            if (withClearCache) clearCache(order)

            // Update RemoteKey for this query.
            coinsDao.insertOrReplaceRemoteOffset(nextOffset)
            coinsDao.insertCoins(coins)
        }
    }

    /**
     * Clear cache for order
     *
     * @param order
     */
    private suspend fun clearCache(order: Order) {
        coinsDao.clearCacheForOrder(order)
        coinsDao.remoteOffsetByOrder(order)
    }

    fun load(order: Order): PagingSource<Int, CoinEntity> {
        return coinsDao.pagingSourceByOrder(order)
    }

    /**
     * Get oldest time when coin is added to cache for order
     *
     * @param order
     * @return
     */
    suspend fun getAddedAtOrder(order: Order): Long {
        return try {
            coinsDao.getAddedAtOrder(order)
        } catch (e: Exception) {
            -1L
        }
    }

    /**
     * Fully clear cache (remoteOffset and coins)
     */
    suspend fun clearCache() {
        coinsDao.clearAllCache()
        coinsDao.clearRemoteOffset()
    }
}