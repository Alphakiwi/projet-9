package com.openclassrooms.realestatemanager.database.database


import android.database.Cursor

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {


    @get:Query("SELECT * FROM Property ")
    val properties: LiveData<List<Property>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProperty(property: Property): Long

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    fun getPropertiesWithCursor(propertyId: Int): Cursor

   // @Query("SELECT * FROM Property WHERE type LIKE :type AND price BETWEEN :priceMin  AND  :priceMax AND  nb_bedroom AND surface BETWEEN :surfaceMin  AND :surfaceMax AND  nb_piece BETWEEN :pieceMin AND :pieceMax  AND  description LIKE :descript AND  ville LIKE :ville AND  address LIKE :address AND proximity LIKE :proximity AND  status LIKE :statu AND  date(start_date) > date(:startDate)  AND  date(selling_date) > date(:sellingDate)   AND estate_agent LIKE :agent AND priceIsDollar LIKE :isDollar and  nb_photo BETWEEN :photoMin  AND  :photoMax AND  nb_video  BETWEEN :videoMin  AND  :videoMax")
   // fun findCorrectProperties(type: String, priceMin: Int, surfaceMin: Int, pieceMin: Int, priceMax: Int, surfaceMax: Int, pieceMax: Int, descript: String, ville: String, address: String, proximity: String, statu: String, startDate: String, sellingDate: String, agent: String, isDollar: String, photoMin: Int, photoMax: Int, videoMin: Int, videoMax: Int): LiveData<List<Property>>
}