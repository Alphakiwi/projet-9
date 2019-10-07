package com.openclassrooms.realestatemanager.database.injections

import android.content.Context


import com.openclassrooms.realestatemanager.database.database.SaveMyData
import com.openclassrooms.realestatemanager.database.repositories.ImageDataRepository
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository

import java.util.concurrent.Executor
import java.util.concurrent.Executors

object Injection {


    fun provideItemDataSource(context: Context): PropertyDataRepository {
        val database = SaveMyData.getInstance(context)
        return PropertyDataRepository(database!!.propertyDao())
    }

    fun provideVideoDataSource(context: Context): VideoDataRepository {
        val database = SaveMyData.getInstance(context)
        return VideoDataRepository(database!!.videoDao())
    }

    fun provideImageDataSource(context: Context): ImageDataRepository {
        val database = SaveMyData.getInstance(context)
        return ImageDataRepository(database!!.imageDao())
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSourceItem = provideItemDataSource(context)
        val videoSourceItem = provideVideoDataSource(context)
        val imageSourceItem = provideImageDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(dataSourceItem, videoSourceItem, imageSourceItem, executor)
    }
}