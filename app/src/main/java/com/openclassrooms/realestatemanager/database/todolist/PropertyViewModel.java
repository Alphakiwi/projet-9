package com.openclassrooms.realestatemanager.database.todolist;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // REPOSITORIES
    private final PropertyDataRepository propertyDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Property>> currentProperties;

    public PropertyViewModel(PropertyDataRepository propertyDataSource, Executor executor) {
        this.propertyDataSource = propertyDataSource;
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

    public LiveData<List<Property>> findCorrectProperties(String type, String priceMin, String bedMin, String bathMin, String surfaceMin, String pieceMin, String priceMax, String bedMax, String bathMax, String surfaceMax, String pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar) {
        return propertyDataSource.findCorrectProperties( type, priceMin, bedMin,bathMin,  surfaceMin, pieceMin,  priceMax,  bedMax,  bathMax, surfaceMax,  pieceMax, descript,  ville,  address,  proximity,  statu,  startDate,  sellingDate, agent,  isDollar) ;
    }


    public void createProperty(Property property) {
        executor.execute(() -> {
            propertyDataSource.createProperty(property);
        });
    }



}