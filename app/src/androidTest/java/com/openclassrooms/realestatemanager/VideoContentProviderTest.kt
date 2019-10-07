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
import com.openclassrooms.realestatemanager.database.provider.VideoContentProvider

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat

@RunWith(AndroidJUnit4::class)
class VideoContentProviderTest {

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
    fun getVideosWhenNoVideoInserted() {
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(VideoContentProvider.URI_VIDEO, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }

    @Test
    fun insertAndGetVideo() {
        // BEFORE : Adding demo video
        val propertyUri = mContentResolver!!.insert(VideoContentProvider.URI_VIDEO, generateVideo())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(VideoContentProvider.URI_VIDEO, PROPERTY_ID.toLong()), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("video")), `is`("https://www.youtube.com/watch?v=6qtQhCGS22c"))
    }

    // ---

    private fun generateVideo(): ContentValues {
        val values = ContentValues()
        values.put("id_property", 666)
        values.put("video", "https://www.youtube.com/watch?v=6qtQhCGS22c")
        return values
    }

    companion object {

        // DATA SET FOR TEST
        private val PROPERTY_ID = 666
    }
}