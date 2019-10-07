package com.openclassrooms.realestatemanager.database.todolist


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.openclassrooms.realestatemanager.database.repositories.ImageDataRepository
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Video_property
import java.util.concurrent.Executor

class PropertyViewModel(// REPOSITORIES
        private val propertyDataSource: PropertyDataRepository, private val videoDataSource: VideoDataRepository, private val imageDataSource: ImageDataRepository, private val executor: Executor) : ViewModel() {

    // DATA
    private var currentProperties: LiveData<List<Property>>? = null


    val properties: LiveData<List<Property>>
        get() = propertyDataSource.properties

    val videos: LiveData<List<Video_property>>
        get() = videoDataSource.videos


    val images: LiveData<List<Image_property>>
        get() = imageDataSource.images

    fun init() {
        if (this.currentProperties != null) {
            return
        }
        currentProperties = propertyDataSource.properties
    }

    fun createVideo(video: Video_property) {
        executor.execute { videoDataSource.createVideo(video) }
    }

    fun deleteVideo(videoId: Int) {
        executor.execute { videoDataSource.deleteVideo(videoId) }
    }

    fun createImage(image: Image_property) {
        executor.execute { imageDataSource.createImage(image) }
    }

    fun deleteImage(imageId: Int) {
        executor.execute { imageDataSource.deleteImage(imageId) }
    }


    fun findCorrectProperties(type: String, priceMin: Int, surfaceMin: Int, pieceMin: Int, priceMax: Int, surfaceMax: Int, pieceMax: Int, descript: String, ville: String, address: String, proximity: String, statu: String, startDate: String, sellingDate: String, agent: String, isDollar: String, photoMin: Int, photoMax: Int, videoMin: Int, videoMax: Int): LiveData<List<Property>> {
        return propertyDataSource.findCorrectProperties(type, priceMin, surfaceMin, pieceMin, priceMax, surfaceMax, pieceMax, descript, ville, address, proximity, statu, startDate, sellingDate, agent, isDollar, photoMin, photoMax, videoMin, videoMax)
    }


    fun createProperty(property: Property) {
        executor.execute { propertyDataSource.createProperty(property) }
    }


}