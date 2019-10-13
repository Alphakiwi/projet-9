package com.openclassrooms.realestatemanager.database.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

import com.openclassrooms.realestatemanager.database.database.SaveMyData
import com.openclassrooms.realestatemanager.model.Property

class PropertyContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        if (context != null) {
            val propertyId = ContentUris.parseId(uri).toInt()
            val cursor = SaveMyData.getInstance(context!!)!!.propertyDao().getPropertiesWithCursor(propertyId)
            cursor.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        if (context != null) {
            val id = SaveMyData.getInstance(context!!)!!.propertyDao().insertProperty(Property.fromContentValues(contentValues!!))
            if (id != 0L) {
                context!!.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }

        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int { return 0 }
    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int { return 0 }

    companion object {

        // FOR DATA
        val AUTHORITY = "com.openclassrooms"
        val TABLE_NAME = Property::class.java.simpleName
        val URI_PROPERTY = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}