package com.openclassrooms.realestatemanager.database.repositories;


import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.database.VideoPropertyDao;
import com.openclassrooms.realestatemanager.model.Video_property;

import java.util.List;

public class VideoDataRepository {

    private final VideoPropertyDao videoDao;

    public VideoDataRepository(VideoPropertyDao videoDao) { this.videoDao = videoDao; }

    // --- GET ---

    public LiveData<List<Video_property>> getVideos(){ return this.videoDao.getVideos(); }

    // --- CREATE ---

    public void createVideo(Video_property video){ videoDao.insertVideo(video); }

    // --- DELETE ---
    public void deleteVideo(int videoId){ videoDao.deleteVideo(videoId); }


}
