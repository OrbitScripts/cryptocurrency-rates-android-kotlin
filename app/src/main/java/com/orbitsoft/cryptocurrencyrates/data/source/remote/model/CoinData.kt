package com.orbitsoft.cryptocurrencyrates.data.source.remote.model

import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity
import com.squareup.moshi.Json
import java.util.*

data class CoinData(
    /**
     * UUID of the coin
     */
    @field:Json(name = "uuid") val uuid: String,

    /**
     * Currency symbol
     */
    @field:Json(name = "symbol") val symbol: String,

    /**
     * Name of the coin
     */
    @field:Json(name = "name") val name: String,

    /**
     * Main HEX color of the coin
     */
    @field:Json(name = "color") val color: String?,

    /**
     * Location of the icon
     */
    @field:Json(name = "iconUrl") val iconUrl: String?,

    /**
     * Market capitalization. Price times circulating supply
     */
    @field:Json(name = "marketCap") val marketCap: String,

    /**
     * Price of the coin
     */
    @field:Json(name = "price") val price: String,

    /**
     * Price of the coin expressed in Bitcoin
     */
    @field:Json(name = "btcPrice") val btcPrice: String,

    /**
     * Epoch timestamp of when we started listing the coin.
     */
    @field:Json(name = "listedAt") val listedAt: Int,

    /**
     * Percentage of change over the given time period
     */
    @field:Json(name = "change") val change: String,

    /**
     * The position in the ranks
     */
    @field:Json(name = "rank") val rank: Int,

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    @field:Json(name = "sparkline") val sparkline: List<Float?>,

    /**
     * Where to find the coin on coinranking.com
     */
    @field:Json(name = "coinrankingUrl") val coinrankingUrl: String,

    /**
     * 24h trade volume
     */
    @field:Json(name = "24hVolume") val volume24h: String,
)

fun CoinData.asCoinEntity(order: Order, position: Long, addedAt: Long) = CoinEntity(
    uuid = uuid,
    localUuid = UUID.nameUUIDFromBytes(
        "${uuid}${order.field}${order.direction}".toByteArray()
    ).toString(),
    order = order,
    addedAt = addedAt,
    position = position,
    symbol = symbol,
    name = name,
    color = color,
    iconUrl = iconUrl,
    marketCap = marketCap,
    price = price,
    btcPrice = btcPrice,
    listedAt = listedAt,
    change = change,
    rank = rank,
    sparkline = sparkline,
    coinrankingUrl = coinrankingUrl,
    volume24h = volume24h
)

fun List<CoinData>.asListOfCoinEntity(
    order: Order,
    startPosition: Long,
    addedAt: Long
): List<CoinEntity> {
    var p = startPosition
    return this.map {
        it.asCoinEntity(order, ++p, addedAt)
    }
}