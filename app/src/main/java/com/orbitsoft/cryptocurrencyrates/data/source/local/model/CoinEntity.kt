package com.orbitsoft.cryptocurrencyrates.data.source.local.model

import androidx.room.Entity
import com.orbitsoft.cryptocurrencyrates.data.Order

@Entity(
    tableName = CoinEntity.COINS_TABLE,
    primaryKeys = ["uuid", "localUuid", "order", "position"]
)
data class CoinEntity(
    /**
     * UUID of the coin
     */
    val uuid: String,

    /**
     * UUID of the coin inside local db (generated from uuid and order and addedAt)
     */
    val localUuid: String,

    /**
     * Order
     */
    val order: Order,


    /**
     * When record was saved
     */
    val addedAt: Long,

    /**
     * Position in request
     */
    val position: Long,

    /**
     * Currency symbol
     */
    val symbol: String,

    /**
     * Name of the coin
     */
    val name: String,

    /**
     * Main HEX color of the coin
     */
    val color: String?,

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
     * Price of the coin expressed in Bitcoin
     */
    val btcPrice: String,

    /**
     * Epoch timestamp of when we started listing the coin.
     */
    val listedAt: Int,

    /**
     * Percentage of change over the given time period
     */
    val change: String,

    /**
     * The position in the ranks
     */
    val rank: Int,

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    val sparkline: List<Float?>,

    /**
     * Where to find the coin on coinranking.com
     */
    val coinrankingUrl: String,

    /**
     * 24h trade volume
     */
    val volume24h: String,
) {

    companion object {
        const val COINS_TABLE = "coins"
    }
}