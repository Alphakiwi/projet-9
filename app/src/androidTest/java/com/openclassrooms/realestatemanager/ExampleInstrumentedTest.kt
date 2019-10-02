package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import android.net.wifi.WifiManager.WIFI_STATE_ENABLED
import com.openclassrooms.realestatemanager.utils.Utils.haveInternetConnection
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.openclassrooms.realestatemanager", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun verifyWifi() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()


        val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifi = wifiManager.wifiState

        if (wifi == WIFI_STATE_ENABLED) {
            assertTrue(haveInternetConnection(appContext))
        } else {
            assertFalse(haveInternetConnection(appContext))
        }
    }
}

