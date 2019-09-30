package com.openclassrooms.realestatemanager.database.injections;

import android.content.Context;


import com.openclassrooms.realestatemanager.database.database.SaveMyData;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.VideoDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {



    public static PropertyDataRepository provideItemDataSource(Context context) {
        SaveMyData database = SaveMyData.getInstance(context);
        return new PropertyDataRepository(database.propertyDao());
    }

    public static VideoDataRepository provideVideoDataSource(Context context) {
        SaveMyData database = SaveMyData.getInstance(context);
        return new VideoDataRepository(database.videoDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        PropertyDataRepository dataSourceItem = provideItemDataSource(context);
        VideoDataRepository videoSourceItem = provideVideoDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, videoSourceItem, executor);
    }
}