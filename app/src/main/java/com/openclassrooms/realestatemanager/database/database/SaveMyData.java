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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.security.auth.callback.Callback;

@Database(entities = { Property.class},
        version = 1, exportSchema = false)
public abstract class SaveMyData extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyData INSTANCE;

    // --- DAO ---
    public abstract PropertyDao propertyDao();

    // --- INSTANCE ---
    public static SaveMyData getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyData.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.
                                    getApplicationContext(),
                            SaveMyData.class, "Database3.db")
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
/*
                Vector<String> youtubeVideos = new Vector<String>();

                youtubeVideos.add("https://www.youtube.com/watch?v=IdvFBL4Kalo");
                youtubeVideos.add("https://www.youtube.com/watch?v=Vg729rnWsm0");



                List<Image_property> couleurs = Arrays.asList(new Image_property("https://www.maisons-ossature-bois-chalets-charpente-favre-felix.com/images/maison-bois/maison-bois-65.jpg",
                        "Vue extérieur"), new Image_property("https://listspirit.com/wp-content/uploads/2017/08/deco-salon-amenagement-interieur-moderne-dune-maison-au-canada.jpg", "Vue intérieur"),
                        new Image_property(
                                "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg",
                                "Chambre"
                        ), new Image_property(
                                "http://www.bainsetsolutions.fr/scripts/files/5603e6f7554961.58606024/perspective-3d-1-renovation-salle-de-bain-st-gilles-bainsetsolutions-pace-rennes.jpg",
                                "Salle de bain"));
                List<Image_property> couleurs2 = Arrays.asList(new Image_property("https://q-ec.bstatic.com/images/hotel/max1024x768/480/48069729.jpg", "descrip"));

                Property property = new Property(1, "Maison", 120000, 3, 1, 135, 4, "belle maison", couleurs, null, "Lille", "27 Rue Nationale, 59000 Lille", "école, métro", "vendu", "26/06/1999", "28/06/1999", "Denis", "Euro");
                Property appart = new Property(2, "Appartement", 70000, 3, 1, 135, 4, "belle maison", couleurs2, youtubeVideos, "Villeneuve d'Ascq", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq", "école, métro", "à vendre", "26/06/1999", null, "Denis", "Euro");
*/

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
                contentValues.put("video", "https://www.youtube.com/watch?v=IdvFBL4Kalo");
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
                contentValues2.put("video","https://www.youtube.com/watch?v=IdvFBL4Kalo");
                contentValues2.put("ville", "Villeneuve-d'Ascq");
                contentValues2.put("address", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq");
                contentValues2.put("proximity", "école, métro");
                contentValues2.put("status", "à vendre");
                contentValues2.put("start_date", "26/06/1999");
                contentValues2.put("selling_date",  (byte[]) null);
                contentValues2.put("estate_agent", "Denis");
                contentValues2.put("priceIsDollar", "Euro");

                db.insert("Property", OnConflictStrategy.IGNORE, contentValues2);

            }
        };
    }
}
