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

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat

@RunWith(AndroidJUnit4::class)
class ImageContentProviderTest {

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
    fun getImagesWhenNoOtherImageInserted() {
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(ImageContentProvider.URI_IMAGE, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }

    @Test
    fun insertAndGetImage() {
        // BEFORE : Adding demo image
        val propertyUri = mContentResolver!!.insert(ImageContentProvider.URI_IMAGE, generateImage())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(ImageContentProvider.URI_IMAGE, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToPosition(0), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), `is`("Belle piscine !"))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("image")), `is`("https://www.maisons-elytis-lyonouest.fr/wp-content/uploads/Maison-G.-REYRIEUX-HD-3-1300x868.jpg"))

    }

    // ---

    private fun generateImage(): ContentValues {
        val values = ContentValues()
        values.put("id_property", 666)
        values.put("description", "Belle piscine !")
        values.put("image", "https://www.maisons-elytis-lyonouest.fr/wp-content/uploads/Maison-G.-REYRIEUX-HD-3-1300x868.jpg")

        return values
    }

    companion object {

        // DATA SET FOR TEST
        private val PROPERTY_ID = 666
    }
}