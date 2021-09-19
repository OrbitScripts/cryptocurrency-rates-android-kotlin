package com.orbitsoft.cryptocurrencyrates.data.source.remote.model

import com.squareup.moshi.Json

data class CoinsResponseData(
    /**
     * Status of the request
     */
    @field:Json(name = "status") val status: String,
    @field:Json(name = "data") val data: DataData,
)