package com.orbitsoft.cryptocurrencyrates.data

sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val throwable: Throwable) : DataResult<Nothing>()

}

fun <TIn, TOut> DataResult<TIn>.map(transform: (TIn) -> TOut) =
    when (this) {
        is DataResult.Success -> DataResult.Success(transform(this.data))
        is DataResult.Error -> this
    }


