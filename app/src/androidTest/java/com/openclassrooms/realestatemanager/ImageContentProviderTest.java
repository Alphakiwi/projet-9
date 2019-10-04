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
import com.openclassrooms.realestatemanager.database.provider.ImageContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ImageContentProviderTest {

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
    public void getImagesWhenNoOtherImageInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ImageContentProvider.URI_IMAGE, PROPERTY_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetImage() {
        // BEFORE : Adding demo image
        final Uri propertyUri = mContentResolver.insert(ImageContentProvider.URI_IMAGE, generateImage());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ImageContentProvider.URI_IMAGE, PROPERTY_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToPosition(0), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), is("Belle piscine !"));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("image")), is("https://www.maisons-elytis-lyonouest.fr/wp-content/uploads/Maison-G.-REYRIEUX-HD-3-1300x868.jpg"));

    }

    // ---

    private ContentValues generateImage(){
        final ContentValues values = new ContentValues();
        values.put("id_property", 666);
        values.put("description", "Belle piscine !");
        values.put("image", "https://www.maisons-elytis-lyonouest.fr/wp-content/uploads/Maison-G.-REYRIEUX-HD-3-1300x868.jpg");

        return values;
    }
}