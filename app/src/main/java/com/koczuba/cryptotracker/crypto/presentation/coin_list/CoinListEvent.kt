package com.koczuba.cryptotracker.crypto.presentation.coin_list

import com.koczuba.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}