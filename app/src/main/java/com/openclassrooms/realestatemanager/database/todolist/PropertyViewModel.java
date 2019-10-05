package com.openclassrooms.realestatemanager.database.todolist;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.database.repositories.ImageDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository;
import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.Video_property;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // REPOSITORIES
    private final PropertyDataRepository propertyDataSource;
    private final VideoDataRepository videoDataSource;
    private final ImageDataRepository imageDataSource;


    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Property>> currentProperties;

    public PropertyViewModel(PropertyDataRepository propertyDataSource, VideoDataRepository videoDataSource, ImageDataRepository imageDataSource, Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.videoDataSource = videoDataSource;
        this.imageDataSource = imageDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.currentProperties != null) {
            return;
        }
        currentProperties = propertyDataSource.getProperties();
    }


    public LiveData<List<Property>> getProperties() {
        return propertyDataSource.getProperties() ;
    }

    public LiveData<List<Video_property>> getVideos() {
        return videoDataSource.getVideos() ;

    }

    public void createVideo(Video_property video) {
        executor.execute(() -> {
            videoDataSource.createVideo(video);
        });
    }

    public void deleteVideo(int videoId) {
        executor.execute(() -> {
            videoDataSource.deleteVideo(videoId);
        });
    }



    public LiveData<List<Image_property>> getImages() {
        return imageDataSource.getImages() ;

    }

    public void createImage(Image_property image) {
        executor.execute(() -> {
            imageDataSource.createImage(image);
        });
    }

    public void deleteImage(int imageId) {
        executor.execute(() -> {
            imageDataSource.deleteImage(imageId);
        });
    }







   public LiveData<List<Property>> findCorrectProperties(String type, String priceMin, String bedMin, String bathMin, String surfaceMin, String pieceMin, String priceMax, String bedMax, String bathMax, String surfaceMax, String pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar, String photoMin, String photoMax, String videoMin, String videoMax) {
        return propertyDataSource.findCorrectProperties( type, priceMin, bedMin,bathMin,  surfaceMin, pieceMin,  priceMax,  bedMax,  bathMax, surfaceMax,  pieceMax, descript,  ville,  address,  proximity,  statu,  startDate,  sellingDate, agent,  isDollar,  photoMin,  photoMax,  videoMin, videoMax) ;
    }


    public void createProperty(Property property) {
        executor.execute(() -> {
            propertyDataSource.createProperty(property);
        });
    }



}