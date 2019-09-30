package com.openclassrooms.realestatemanager.database.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;

@Dao
public interface PropertyDao {


    @Query("SELECT * FROM Property ")
    LiveData<List<Property>> getProperties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProperty(Property property);

 //   @Query("SELECT * FROM Property WHERE type LIKE :type AND price BETWEEN CAST(:priceMin as decimal)  AND  CAST(:priceMax as decimal) AND  nb_bedroom  BETWEEN CAST(:bedMin as decimal) AND  CAST(:bedMax as decimal) AND  nb_bathroom BETWEEN CAST(:bathMin as decimal) AND  CAST(:bathMax as decimal) AND surface BETWEEN CAST(:surfaceMin as decimal) AND  CAST(:surfaceMax as decimal) AND  nb_piece BETWEEN CAST(:pieceMin as decimal) AND  CAST(:pieceMax as decimal) AND  description LIKE :descript AND  ville LIKE :ville AND  address LIKE :address AND proximity LIKE :proximity AND  status LIKE :statu AND  date(start_date) between date(:startDate) AND date('2020-01-01') AND  date(selling_date) between date(:sellingDate) AND date('2020-01-01')  AND estate_agent LIKE :agent AND priceIsDollar LIKE :isDollar" )
 //   LiveData<List<Property>> findCorrectProperties( String type, String priceMin, String bedMin, String bathMin, String surfaceMin, String pieceMin, String priceMax, String bedMax, String bathMax, String surfaceMax, String pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar);
}