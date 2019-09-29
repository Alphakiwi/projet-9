package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.database.PropertyDao;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;
import java.util.Properties;

public class PropertyDataRepository {


    private final PropertyDao propertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) { this.propertyDao = propertyDao; }

    public LiveData<List<Property>> getProperties() { return this.propertyDao.getProperties(); }

    public void createProperty (Property property){ propertyDao.insertProperty(property); }



}
