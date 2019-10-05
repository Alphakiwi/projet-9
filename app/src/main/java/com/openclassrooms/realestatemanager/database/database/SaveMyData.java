package com.openclassrooms.realestatemanager.database.database;


import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

@Database(entities = { Property.class, Video_property.class, Image_property.class},
        version = 1, exportSchema = false)
public abstract class SaveMyData extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyData INSTANCE;

    // --- DAO ---
    public abstract PropertyDao propertyDao();
    public abstract VideoPropertyDao videoDao();
    public abstract ImagePropertyDao imageDao();


    // --- INSTANCE ---
    public static SaveMyData getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyData.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.
                                    getApplicationContext(),
                            SaveMyData.class, "Database18.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);






                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("type", "Maison");
                contentValues.put("price", 120000);
                contentValues.put("nb_bedroom", 3);
                contentValues.put("nb_bathroom", 1);
                contentValues.put("surface", 135);
                contentValues.put("nb_piece", 4);
                contentValues.put("description", "Belle maison");
                contentValues.put("ville", "Lille");
                contentValues.put("address", "27 Rue Nationale, 59000 Lille");
                contentValues.put("proximity", "école, métro");
                contentValues.put("status", "vendu");
                contentValues.put("start_date", "26/06/1999");
                contentValues.put("selling_date", "28/06/1999");
                contentValues.put("estate_agent", "Denis");
                contentValues.put("priceIsDollar", "Euro");
                contentValues.put("nb_photo", 1);
                contentValues.put("nb_video", 0);



                db.insert("Property", OnConflictStrategy.IGNORE, contentValues);

                ContentValues contentValuesPHOTO = new ContentValues();
                contentValuesPHOTO.put("id", 1);
                contentValuesPHOTO.put("id_property", 1);
                contentValuesPHOTO.put("image", "file:///storage/emulated/0/Pictures/CameraDemo/IMG_20191003_190647.jpg");
                contentValuesPHOTO.put("description", "jolie");



                db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO);

                db.insert("Property", OnConflictStrategy.IGNORE, contentValues);

                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("id", 2);
                contentValues2.put("type", "Appartement");
                contentValues2.put("price", 70000);
                contentValues2.put("nb_bedroom", 3);
                contentValues2.put("nb_bathroom", 1);
                contentValues2.put("surface", 135);
                contentValues2.put("nb_piece", 4);
                contentValues2.put("description", "Bel Appartment");
                contentValues2.put("ville", "Villeneuve-d'Ascq");
                contentValues2.put("address", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq");
                contentValues2.put("proximity", "école, métro");
                contentValues2.put("status", "à vendre");
                contentValues2.put("start_date", "26/06/1999");
                contentValues2.put("selling_date",  "02/01/0000");
                contentValues2.put("estate_agent", "Denis");
                contentValues2.put("priceIsDollar", "Euro");
                contentValues2.put("nb_photo", 2);
                contentValues2.put("nb_video", 0);

                db.insert("Property", OnConflictStrategy.IGNORE, contentValues2);

                ContentValues contentValuesPHOTO2 = new ContentValues();
                contentValuesPHOTO2.put("id", 2);
                contentValuesPHOTO2.put("id_property", 2);
                contentValuesPHOTO2.put("image","file:///storage/emulated/0/Pictures/CameraDemo/IMG_20191003_190647.jpg");
                contentValuesPHOTO2.put("description", "maison");

                db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO2);


                ContentValues contentValuesPHOTO3 = new ContentValues();
                contentValuesPHOTO3.put("id", 3);
                contentValuesPHOTO3.put("id_property", 2);
                contentValuesPHOTO3.put("image", "file:///storage/emulated/0/Pictures/CameraDemo/IMG_20191003_190647.jpg");
                contentValuesPHOTO3.put("description", "yo");



                db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO3);

            }
        };
    }
}
