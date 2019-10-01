package com.openclassrooms.realestatemanager.database.injections;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.ImageDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository;
import com.openclassrooms.realestatemanager.database.todolist.PropertyViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertyDataRepository propertyDataSource;
    private final VideoDataRepository videoDataSource;
    private final ImageDataRepository imageDataSource;
    private final Executor executor;

    public ViewModelFactory(PropertyDataRepository propertyDataSource, VideoDataRepository videoDataSource, ImageDataRepository imageDataSource,  Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.videoDataSource = videoDataSource;
        this.imageDataSource = imageDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(propertyDataSource, videoDataSource , imageDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

