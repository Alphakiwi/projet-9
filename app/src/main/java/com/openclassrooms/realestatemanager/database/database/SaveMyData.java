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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.security.auth.callback.Callback;

@Database(entities = { Property.class, Video_property.class},
        version = 1, exportSchema = false)
public abstract class SaveMyData extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyData INSTANCE;

    // --- DAO ---
    public abstract PropertyDao propertyDao();
    public abstract VideoPropertyDao videoDao();

    // --- INSTANCE ---
    public static SaveMyData getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyData.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.
                                    getApplicationContext(),
                            SaveMyData.class, "Database5.db")
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
                contentValues.put("photo", "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg");
                contentValues.put("ville", "Lille");
                contentValues.put("address", "27 Rue Nationale, 59000 Lille");
                contentValues.put("proximity", "école, métro");
                contentValues.put("status", "vendu");
                contentValues.put("start_date", "26/06/1999");
                contentValues.put("selling_date", "28/06/1999");
                contentValues.put("estate_agent", "Denis");
                contentValues.put("priceIsDollar", "Euro");

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
                contentValues2.put("photo", "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg");
                contentValues2.put("ville", "Villeneuve-d'Ascq");
                contentValues2.put("address", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq");
                contentValues2.put("proximity", "école, métro");
                contentValues2.put("status", "à vendre");
                contentValues2.put("start_date", "26/06/1999");
                contentValues2.put("selling_date",  "0000-01-02");
                contentValues2.put("estate_agent", "Denis");
                contentValues2.put("priceIsDollar", "Euro");

                db.insert("Property", OnConflictStrategy.IGNORE, contentValues2);

            }
        };
    }
}
