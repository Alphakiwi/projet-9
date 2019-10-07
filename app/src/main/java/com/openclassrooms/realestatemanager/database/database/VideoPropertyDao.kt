package com.openclassrooms.realestatemanager.database.database


import android.database.Cursor

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.openclassrooms.realestatemanager.model.Video_property

@Dao
interface VideoPropertyDao {

    @get:Query("SELECT * FROM Video_property ")
    val videos: LiveData<List<Video_property>>

    @Insert
    fun insertVideo(video: Video_property): Long


    @Query("DELETE FROM Video_property WHERE id = :videoId")
    fun deleteVideo(videoId: Int): Int

    @Query("SELECT * FROM Video_property WHERE id_property = :propertyId")
    fun getVideosWithCursor(propertyId: Int): Cursor
}
