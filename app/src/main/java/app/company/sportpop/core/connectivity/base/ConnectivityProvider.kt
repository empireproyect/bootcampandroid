@file:Suppress("DEPRECATION")

package app.company.sportpop.core.connectivity.base

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import app.company.sportpop.core.connectivity.ConnectivityProviderImpl
import app.company.sportpop.core.connectivity.ConnectivityProviderLegacyImpl


interface ConnectivityProvider {
    interface ConnectivityStateListener {
        fun onStateChange(state: NetworkState)
    }

    fun addListener(listener: ConnectivityStateListener)
    fun removeListener(listener: ConnectivityStateListener)

    fun getNetworkState(): NetworkState
    fun isNetworkAvailable() = getNetworkState().isNetworkAvailable()

    sealed class NetworkState {
        object NotConnectedState : NetworkState()

        fun isNetworkAvailable() = (this as? ConnectedState)?.hasInternet == true

        sealed class ConnectedState(val hasInternet: Boolean) : NetworkState() {

            @RequiresApi(Build.VERSION_CODES.N)
            data class Connected(val capabilities: NetworkCapabilities) : ConnectedState(
                capabilities.hasCapability(NET_CAPABILITY_INTERNET)
            )

            data class ConnectedLegacy(val networkInfo: NetworkInfo) : ConnectedState(
                networkInfo.isConnectedOrConnecting
            )
        }
    }

    companion object {
        fun createProvider(context: Context): ConnectivityProvider {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ConnectivityProviderImpl(cm)
            } else {
                ConnectivityProviderLegacyImpl(context, cm)
            }
        }
    }
}
