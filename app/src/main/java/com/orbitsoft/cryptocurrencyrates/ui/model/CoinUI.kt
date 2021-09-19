package com.orbitsoft.cryptocurrencyrates.ui.model

import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity

data class CoinUI(
    /**
     * UUID of the coin
     */
    val localUuid: String,

    /**
     * Currency symbol
     */
    val symbol: String,

    /**
     * Name of the coin
     */
    val name: String,

    /**
     * Location of the icon
     */
    val iconUrl: String?,

    /**
     * Market capitalization. Price times circulating supply
     */
    val marketCap: String?,

    /**
     * Price of the coin
     */
    val price: String,

    /**
     * Percentage of change over the given time period
     */
    val change: String,

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    val sparkline: List<Float?>,

    /**
     * Position
     */
    val position: Long,
)

fun CoinEntity.asCoinUI() = CoinUI(
    localUuid = localUuid,
    symbol = symbol,
    name = name,
    iconUrl = iconUrl,
    marketCap = marketCap,
    price = price,
    change = change,
    sparkline = sparkline,
    position = position
)