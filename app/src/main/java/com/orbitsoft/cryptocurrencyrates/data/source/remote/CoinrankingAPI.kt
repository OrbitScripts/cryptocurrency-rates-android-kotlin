package com.orbitsoft.cryptocurrencyrates.data.source.remote

import com.orbitsoft.cryptocurrencyrates.data.source.remote.model.CoinsResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinrankingAPI {
    companion object {
        const val ORDER_MARKET_CAP = "marketCap"
        const val ORDER_CHANGE = "change"
        const val ORDER_DIRECTION_ASC = "asc"
        const val ORDER_DIRECTION_DESC = "desc"
    }

    @GET("coins")
    suspend fun loadCoins(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") order: String,
        @Query("orderDirection") orderDirection: String
    ): CoinsResponseData
}