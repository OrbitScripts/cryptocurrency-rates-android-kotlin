package com.orbitsoft.cryptocurrencyrates.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orbitsoft.cryptocurrencyrates.data.source.local.CoinsDao
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.CoinEntity
import com.orbitsoft.cryptocurrencyrates.data.source.local.model.RemoteOffsetEntity

@Database(entities = [CoinEntity::class, RemoteOffsetEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinsDao(): CoinsDao
}