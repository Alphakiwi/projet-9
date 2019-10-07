package com.openclassrooms.realestatemanager.database.database


import android.database.Cursor

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Video_property

@Dao
interface ImagePropertyDao {

    @get:Query("SELECT * FROM Image_property ")
    val images: LiveData<List<Image_property>>

    @Insert
    fun insertImage(image: Image_property): Long


    @Query("DELETE FROM Image_property WHERE id = :imageId")
    fun deleteImage(imageId: Int): Int

    @Query("SELECT * FROM Image_property WHERE id_property = :propertyId")
    fun getImagesWithCursor(propertyId: Int): Cursor
}
