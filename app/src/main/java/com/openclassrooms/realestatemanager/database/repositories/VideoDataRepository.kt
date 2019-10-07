package com.openclassrooms.realestatemanager.database.repositories


import androidx.lifecycle.LiveData

import com.openclassrooms.realestatemanager.database.database.VideoPropertyDao
import com.openclassrooms.realestatemanager.model.Video_property

class VideoDataRepository(private val videoDao: VideoPropertyDao) {

    // --- GET ---

    val videos: LiveData<List<Video_property>>
        get() = this.videoDao.videos

    // --- CREATE ---

    fun createVideo(video: Video_property) {
        videoDao.insertVideo(video)
    }

    // --- DELETE ---
    fun deleteVideo(videoId: Int) {
        videoDao.deleteVideo(videoId)
    }


}
