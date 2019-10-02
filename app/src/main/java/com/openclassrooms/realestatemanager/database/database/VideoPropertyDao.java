package com.openclassrooms.realestatemanager.database.database;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.Video_property;

import java.util.List;

@Dao
public interface VideoPropertyDao {

    @Query("SELECT * FROM Video_property ")
    LiveData<List<Video_property>> getVideos();

    @Insert
    long insertVideo(Video_property video);


    @Query("DELETE FROM Video_property WHERE id = :videoId")
    int deleteVideo(int videoId);

    @Query("SELECT * FROM Video_property WHERE id_property = :propertyId")
    Cursor getVideosWithCursor(int propertyId);
}
