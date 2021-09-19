package com.orbitsoft.cryptocurrencyrates.data.source.remote.model

import com.squareup.moshi.Json

data class StatsData(
    /**
     * Total number of coins within the query
     */
    @field:Json(name = "total") val total: Int,

    /**
     * Total number of markets within the query
     */
    @com.squareup.moshi.Json(name = "totalMarkets") val totalMarkets: Int,

    /**
     * Total number of exchanges within the query
     */
    @field:Json(name = "totalExchanges") val totalExchanges: Int,

    /**
     * The market capital of coins within the query
     */
    @field:Json(name = "totalMarketCap") val totalMarketCap: String,

    /**
     * The volume over the last 24 hours of coins within the query
     */
    @field:Json(name = "total24hVolume") val total24hVolume: String
)