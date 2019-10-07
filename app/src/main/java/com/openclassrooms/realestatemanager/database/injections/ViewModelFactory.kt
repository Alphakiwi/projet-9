package com.openclassrooms.realestatemanager.database.injections


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.openclassrooms.realestatemanager.database.repositories.ImageDataRepository
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository
import com.openclassrooms.realestatemanager.database.todolist.PropertyViewModel

import java.util.concurrent.Executor

class ViewModelFactory(private val propertyDataSource: PropertyDataRepository, private val videoDataSource: VideoDataRepository, private val imageDataSource: ImageDataRepository, private val executor: Executor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            return PropertyViewModel(propertyDataSource, videoDataSource, imageDataSource, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

