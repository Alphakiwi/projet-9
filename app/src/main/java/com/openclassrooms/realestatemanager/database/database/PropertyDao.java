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
}