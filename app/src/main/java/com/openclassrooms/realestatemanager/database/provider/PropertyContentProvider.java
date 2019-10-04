package com.openclassrooms.realestatemanager.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.database.database.SaveMyData;
import com.openclassrooms.realestatemanager.model.Property;

public class PropertyContentProvider extends ContentProvider {

    // FOR DATA
    public static final String AUTHORITY = "com.openclassrooms";
    public static final String TABLE_NAME = Property.class.getSimpleName();
    public static final Uri URI_PROPERTY = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() { return true; }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (getContext() != null){
            int  propertyId = (int) ContentUris.parseId(uri);
            final Cursor cursor = SaveMyData.getInstance(getContext()).propertyDao().getPropertiesWithCursor(propertyId);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (getContext() != null){
            final long id = SaveMyData.getInstance(getContext()).propertyDao().insertProperty(Property.CREATOR.fromContentValues(contentValues));
            if (id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }

        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}