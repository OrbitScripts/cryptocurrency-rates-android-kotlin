package com.orbitsoft.cryptocurrencyrates.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity.Companion.COINS_TABLE
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.RemoteOffsetEntity
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.RemoteOffsetEntity.Companion.REMOTE_OFFSET_TABLE

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Query("SELECT * FROM $COINS_TABLE $WHERE")
    fun pagingSourceByOrder(order: Order): PagingSource<Int, CoinEntity>

    @Query("SELECT addedAt FROM $COINS_TABLE $WHERE ORDER BY addedAt LIMIT 1")
    suspend fun getAddedAtOrder(order: Order): Long

    @Query("DELETE FROM $COINS_TABLE $WHERE")
    suspend fun clearCacheForOrder(order: Order): Int

    @Query("DELETE FROM $COINS_TABLE")
    suspend fun clearAllCache(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceRemoteOffset(remoteOffset: RemoteOffsetEntity)

    @Query("SELECT * FROM $REMOTE_OFFSET_TABLE $WHERE")
    suspend fun remoteOffsetByOrder(
        order: Order,
    ): RemoteOffsetEntity?

    @Query("DELETE FROM $REMOTE_OFFSET_TABLE $WHERE")
    suspend fun deleteRemoteOffsetByOrder(order: Order)

    @Query("DELETE FROM $REMOTE_OFFSET_TABLE")
    suspend fun clearRemoteOffset(): Int

    companion object {
        private const val WHERE = " WHERE `order` = :order"
    }
}