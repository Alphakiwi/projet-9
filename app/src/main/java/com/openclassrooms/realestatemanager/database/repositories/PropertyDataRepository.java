package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.database.PropertyDao;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;

public class PropertyDataRepository {


    private final PropertyDao propertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) { this.propertyDao = propertyDao; }

    public LiveData<List<Property>> getProperties() { return this.propertyDao.getProperties(); }

    public void createProperty (Property property){ propertyDao.insertProperty(property); }

   // public LiveData<List<Property>> findCorrectProperties(String type, String priceMin, String bedMin, String bathMin, String surfaceMin, String pieceMin, String priceMax, String bedMax, String bathMax, String surfaceMax, String pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar)
  //  {return propertyDao.findCorrectProperties(type, priceMin, bedMin, bathMin, surfaceMin, pieceMin,  priceMax, bedMax,  bathMax,  surfaceMax,  pieceMax, descript,  ville,  address,  proximity, statu,  startDate, sellingDate, agent, isDollar);}




}
