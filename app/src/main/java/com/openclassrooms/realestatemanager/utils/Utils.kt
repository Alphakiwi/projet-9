@file:Suppress("DEPRECATION")

package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {


    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val getTodayDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            return dateFormat.format(Date())
        }

    val getTodayDate2: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            return dateFormat.format(Date())
        }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return Math.round(dollars * 0.812).toInt()
    }

    fun convertEuroToDollar(euro: Int): Int {
        return Math.round(euro / 0.812 + euro % 0.812).toInt()
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }


    @JvmStatic
    fun haveInternetConnection(context: Context): Boolean {
        // Fonction haveInternetConnection : return true if connected, return false if not
        val network = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

        return !(network == null || !network.isConnected)

    }


    fun isInteger(s: String): Boolean {
        if (s.isEmpty()) return false
        for (i in 0 until s.length) {
            if (i == 0 && s[i] == '-') {
                return if (s.length == 1)
                    false
                else
                    continue
            }
            if (Character.digit(s[i], 10) < 0) return false
        }
        return true
    }
}

