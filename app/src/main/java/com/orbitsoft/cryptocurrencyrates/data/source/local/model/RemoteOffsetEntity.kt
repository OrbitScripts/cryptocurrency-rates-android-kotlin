package com.orbitsoft.cryptocurrencyrates.data.source.local.model

import androidx.room.Entity
import com.orbitsoft.cryptocurrencyrates.data.Order

@Entity(
    tableName = RemoteOffsetEntity.REMOTE_OFFSET_TABLE,
    primaryKeys = ["order"]
)
data class RemoteOffsetEntity(
    /**
     * Next offset for fetch from remote source
     */
    val nextOffset: Int,

    /**
     * Order
     */
    val order: Order,

    ) {

    companion object {
        const val REMOTE_OFFSET_TABLE = "remote_offset"
    }
}