package com.orbitsoft.cryptocurrencyrates.data.source.remote

import com.orbitsoft.cryptocurrencyrates.data.DataResult
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingAPI.Companion.ORDER_CHANGE
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingAPI.Companion.ORDER_DIRECTION_ASC
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingAPI.Companion.ORDER_DIRECTION_DESC
import com.orbitsoft.cryptocurrencyrates.data.source.remote.CoinrankingAPI.Companion.ORDER_MARKET_CAP
import com.orbitsoft.cryptocurrencyrates.data.source.remote.model.DataData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinrankingSource @Inject constructor(private val api: CoinrankingAPI) {

    suspend fun loadCoins(offset: Int, limit: Int, order: Order): DataResult<DataData> {
        try {
            val response = api.loadCoins(
                offset = offset,
                limit = limit,
                order = when (order.field) {
                    Order.OrderField.MARKET_CAP -> ORDER_MARKET_CAP
                    Order.OrderField.CHANGE -> ORDER_CHANGE
                },
                orderDirection = when (order.direction) {
                    Order.Direction.DESC -> ORDER_DIRECTION_DESC
                    Order.Direction.ASC -> ORDER_DIRECTION_ASC
                }
            )
            return DataResult.Success(
                data = response.data
            )
        } catch (e: Exception) {
            return DataResult.Error(
                throwable = e
            )
        }
    }
}