package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.database.SaveMyData;
import com.openclassrooms.realestatemanager.database.provider.VideoContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class VideoContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static int PROPERTY_ID = 666;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                SaveMyData.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getVideosWhenNoVideoInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(VideoContentProvider.URI_VIDEO, PROPERTY_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetVideo() {
        // BEFORE : Adding demo video
        final Uri propertyUri = mContentResolver.insert(VideoContentProvider.URI_VIDEO, generateVideo());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(VideoContentProvider.URI_VIDEO, PROPERTY_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("video")), is("https://www.youtube.com/watch?v=6qtQhCGS22c"));
    }

    // ---

    private ContentValues generateVideo(){
        final ContentValues values = new ContentValues();
        values.put("id_property", 666);
        values.put("video", "https://www.youtube.com/watch?v=6qtQhCGS22c");
        return values;
    }
}