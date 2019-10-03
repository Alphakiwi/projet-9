package com.openclassrooms.realestatemanager.database.database;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

import java.util.List;

@Dao
public interface ImagePropertyDao {

    @Query("SELECT * FROM Image_property ")
    LiveData<List<Image_property>> getImages();

    @Insert
    long insertImage(Image_property image);


    @Query("DELETE FROM Image_property WHERE id = :imageId")
    int deleteImage(int imageId);

    @Query("SELECT * FROM Image_property WHERE id_property = :propertyId")
    Cursor getImagesWithCursor(int propertyId);
}
