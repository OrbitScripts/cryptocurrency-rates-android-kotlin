package com.orbitsoft.cryptocurrencyrates.di.modules

import android.content.Context
import androidx.room.Room
import com.orbitsoft.cryptocurrencyrates.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext appContext: Context?) =
        Room.databaseBuilder(appContext!!, AppDatabase::class.java, "coins_db")
            .build()

}