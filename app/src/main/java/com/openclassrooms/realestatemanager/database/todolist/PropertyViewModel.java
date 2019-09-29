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


    public void createProperty(Property property) {
        executor.execute(() -> {
            propertyDataSource.createProperty(property);
        });
    }



}