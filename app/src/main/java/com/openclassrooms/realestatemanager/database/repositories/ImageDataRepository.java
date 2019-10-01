package com.openclassrooms.realestatemanager.database.repositories;


import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.database.ImagePropertyDao;
import com.openclassrooms.realestatemanager.database.database.VideoPropertyDao;
import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

import java.util.List;

public class ImageDataRepository {

    private final ImagePropertyDao imageDao;

    public ImageDataRepository(ImagePropertyDao imageDao) { this.imageDao = imageDao; }

    // --- GET ---

    public LiveData<List<Image_property>> getImages(){ return this.imageDao.getImages(); }

    // --- CREATE ---

    public void createImage(Image_property image){ imageDao.insertImage(image); }

    // --- DELETE ---
    public void deleteImage(int imageId){ imageDao.deleteImage(imageId); }


}
