package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.getTodayDate
import com.openclassrooms.realestatemanager.utils.Utils.isInteger
import org.junit.Test


import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun convertEuroToDollar_isCorrect() {
        val value = Utils.convertEuroToDollar(81)
        assertEquals(100, value.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun convertDollarToEuro_isCorrect() {
        val value = Utils.convertDollarToEuro(100)
        assertEquals(81, value.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun returnDate_withGoodFormat() {
        val today = getTodayDate
        assertTrue(isInteger(today.substring(0, 2)))
        assertTrue(today.get(2) == '/')
        assertTrue(isInteger(today.substring(3, 5)))
        assertTrue(today.get(5) == '/')
        assertTrue(isInteger(today.substring(6, 10)))


    }
}