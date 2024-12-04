package com.koczuba.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koczuba.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.koczuba.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.koczuba.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.koczuba.cryptotracker.core.presentation.util.ObserveAsEvents
import com.koczuba.cryptotracker.core.presentation.util.toString
import com.koczuba.cryptotracker.crypto.presentation.coin_details.CoinDetailsScreen
import com.koczuba.cryptotracker.crypto.presentation.coin_list.CoinListEvent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when (event) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    when {
                        state.selectedCoin != null -> {
                            CoinDetailsScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        else ->
                            CoinListScreen(
                                state = state,
                                onAction = viewModel::onAction,
                                modifier = Modifier.padding(innerPadding)
                            )
                    }

                }
            }
        }
    }
}
