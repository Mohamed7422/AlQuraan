package com.example.alquran.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object InternetConnection {


    fun isInternetAvailable(context: Context):Boolean{
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities!=null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                 networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

}