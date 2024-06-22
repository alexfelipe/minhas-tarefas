package br.com.alexf.minhastarefas.connection

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class NetworkState {
    data object Initialization : NetworkState()
    data class Available(val network: Network) : NetworkState()
    data class CapabilitiesChanged(
        val network: Network,
        val networkCapabilities: NetworkCapabilities
    ) : NetworkState()
    data class Lost(val network: Network) : NetworkState()
}

class NetworkConnectionViewModel(
    connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Initialization)
    val networkState = _networkState.asStateFlow()

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkState.update {
                    NetworkState.Available(network)
                }
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                _networkState.update {
                    NetworkState.CapabilitiesChanged(network, networkCapabilities)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkState.update {
                    NetworkState.Lost(network)
                }
            }
        }

        connectivityManager
            .requestNetwork(
                networkRequest,
                networkCallback
            )
    }

}