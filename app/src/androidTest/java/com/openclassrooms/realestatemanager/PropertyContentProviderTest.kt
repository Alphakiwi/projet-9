package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.openclassrooms.realestatemanager.database.database.SaveMyData
import com.openclassrooms.realestatemanager.database.provider.ImageContentProvider
import com.openclassrooms.realestatemanager.database.provider.PropertyContentProvider

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat

@RunWith(AndroidJUnit4::class)
class PropertyContentProviderTest {

    // FOR DATA
    private var mContentResolver: ContentResolver? = null

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                SaveMyData::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getContext().contentResolver
    }

    @Test
    fun getPropertiesWhenNoOtherPropertyInserted() {
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }

    @Test
    fun insertAndGetProperty() {
        // BEFORE : Adding demo property
        val propertyUri = mContentResolver!!.insert(PropertyContentProvider.URI_PROPERTY, generateProperty())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), `is`("Maison"))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("address")), `is`("310 rue Jules Guesde, Villeneuve D'Ascq 59650"))

    }

    // ---

    private fun generateProperty(): ContentValues {
        val values = ContentValues()
        values.put("id", 666)
        values.put("type", "Maison")
        values.put("price", 123000)
        values.put("nb_bedroom", 2)
        values.put("nb_bathroom", 1)
        values.put("surface", 125)
        values.put("nb_piece", 3)
        values.put("description", "Une bonne affaire !")
        values.put("ville", "Villeneuve d'Ascq")
        values.put("address", "310 rue Jules Guesde, Villeneuve D'Ascq 59650")
        values.put("proximity", "bus, crèche")
        values.put("status", "à lour")
        values.put("start_date", "28/06/2019")
        values.put("selling_date", "0000-01-02")
        values.put("estate_agent", "Guillaume")
        values.put("priceIsDollar", "Euro")
        values.put("nb_photo", 1)
        values.put("nb_video", 1)


        return values
    }

    companion object {

        // DATA SET FOR TEST
        private val PROPERTY_ID = 666
    }
}