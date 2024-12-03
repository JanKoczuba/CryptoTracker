package com.koczuba.cryptotracker.crypto.domain

import com.koczuba.cryptotracker.core.domain.util.NetworkError
import com.koczuba.cryptotracker.core.domain.util.Result


interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

}