package com.orbitsoft.cryptocurrencyrates.data.source.remote.model

import com.squareup.moshi.Json

data class DataData(
    /**
     * A series of statistics regarding the requested list.
     * Note that the stats its scope includes coins outside the limit.
     * E.g. the response of a query with a limit of 50 coins returns 50 coins (obviously),
     * while the stats depicts the amount of coins available, 24 hour volume,
     * etc. without this limit, which may be a much higher number
     */
    @field:Json(name = "stats") val stats: StatsData,

    /**
     * List of coins
     */
    @field:Json(name = "coins") val coins: List<CoinData>
)