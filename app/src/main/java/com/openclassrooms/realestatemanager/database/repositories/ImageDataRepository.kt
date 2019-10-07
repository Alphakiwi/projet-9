package com.openclassrooms.realestatemanager.database.repositories


import androidx.lifecycle.LiveData

import com.openclassrooms.realestatemanager.database.database.ImagePropertyDao
import com.openclassrooms.realestatemanager.database.database.VideoPropertyDao
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Video_property

class ImageDataRepository(private val imageDao: ImagePropertyDao) {

    // --- GET ---

    val images: LiveData<List<Image_property>>
        get() = this.imageDao.images

    // --- CREATE ---

    fun createImage(image: Image_property) {
        imageDao.insertImage(image)
    }

    // --- DELETE ---
    fun deleteImage(imageId: Int) {
        imageDao.deleteImage(imageId)
    }


}
