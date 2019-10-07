package com.openclassrooms.realestatemanager.database.repositories

import androidx.lifecycle.LiveData

import com.openclassrooms.realestatemanager.database.database.PropertyDao
import com.openclassrooms.realestatemanager.model.Property

class PropertyDataRepository(private val propertyDao: PropertyDao) {

    val properties: LiveData<List<Property>>
        get() = this.propertyDao.properties

    fun createProperty(property: Property) {
        propertyDao.insertProperty(property)
    }

    fun findCorrectProperties(type: String, priceMin: Int, surfaceMin: Int, pieceMin: Int, priceMax: Int, surfaceMax: Int, pieceMax: Int, descript: String, ville: String, address: String, proximity: String, statu: String, startDate: String, sellingDate: String, agent: String, isDollar: String, photoMin: Int, photoMax: Int, videoMin: Int, videoMax: Int): LiveData<List<Property>> {
        return propertyDao.findCorrectProperties(type, priceMin, surfaceMin, pieceMin, priceMax, surfaceMax, pieceMax, descript, ville, address, proximity, statu, startDate, sellingDate, agent, isDollar, photoMin, photoMax, videoMin, videoMax)
    }


}
