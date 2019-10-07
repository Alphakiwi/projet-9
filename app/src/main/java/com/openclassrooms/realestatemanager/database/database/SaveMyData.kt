package com.openclassrooms.realestatemanager.database.database


import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Video_property

@Database(entities = [Property::class, Video_property::class, Image_property::class], version = 1, exportSchema = false)
abstract class SaveMyData : RoomDatabase() {

    // --- DAO ---
    abstract fun propertyDao(): PropertyDao

    abstract fun videoDao(): VideoPropertyDao
    abstract fun imageDao(): ImagePropertyDao

    companion object {

        // --- SINGLETON ---
        @Volatile
        private var INSTANCE: SaveMyData? = null


        // --- INSTANCE ---
        fun getInstance(context: Context): SaveMyData? {
            if (INSTANCE == null) {
                synchronized(SaveMyData::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                SaveMyData::class.java, "Database24.db")
                                .addCallback(prepopulateDatabase())
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        // ---

        private fun prepopulateDatabase(): RoomDatabase.Callback {
            return object : RoomDatabase.Callback() {

                override fun onCreate(db: SupportSQLiteDatabase) {

                    super.onCreate(db)


                    val contentValues = ContentValues()
                    contentValues.put("id", 1)
                    contentValues.put("type", "Maison")
                    contentValues.put("price", 120000)
                    contentValues.put("nb_bedroom", 3)
                    contentValues.put("nb_bathroom", 1)
                    contentValues.put("surface", 135)
                    contentValues.put("nb_piece", 4)
                    contentValues.put("description", "Belle maison")
                    contentValues.put("ville", "Lille")
                    contentValues.put("address", "27 Rue Nationale, 59000 Lille")
                    contentValues.put("proximity", "métro,école")
                    contentValues.put("status", "vendu")
                    contentValues.put("start_date", "1999-06-26")
                    contentValues.put("selling_date", "1999-06-28")
                    contentValues.put("estate_agent", "Denis")
                    contentValues.put("priceIsDollar", "Euro")
                    contentValues.put("nb_photo", 1)
                    contentValues.put("nb_video", 0)



                    db.insert("Property", OnConflictStrategy.IGNORE, contentValues)

                    val contentValuesPHOTO = ContentValues()
                    contentValuesPHOTO.put("id", 1)
                    contentValuesPHOTO.put("id_property", 1)
                    contentValuesPHOTO.put("image", "https://www.ledrein-courgeon.fr/wp-content/uploads/2015/11/littre-facade-3.jpg")
                    contentValuesPHOTO.put("description", "jolie")



                    db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO)

                    db.insert("Property", OnConflictStrategy.IGNORE, contentValues)

                    val contentValues2 = ContentValues()
                    contentValues2.put("id", 2)
                    contentValues2.put("type", "Appartement")
                    contentValues2.put("price", 70000)
                    contentValues2.put("nb_bedroom", 3)
                    contentValues2.put("nb_bathroom", 1)
                    contentValues2.put("surface", 135)
                    contentValues2.put("nb_piece", 4)
                    contentValues2.put("description", "Bel Appartment")
                    contentValues2.put("ville", "Villeneuve-d'Ascq")
                    contentValues2.put("address", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq")
                    contentValues2.put("proximity", "métro,école")
                    contentValues2.put("status", "à vendre")
                    contentValues2.put("start_date", "1999-06-26")
                    contentValues2.put("selling_date", "0000-01-02")
                    contentValues2.put("estate_agent", "Denis")
                    contentValues2.put("priceIsDollar", "Euro")
                    contentValues2.put("nb_photo", 2)
                    contentValues2.put("nb_video", 0)

                    db.insert("Property", OnConflictStrategy.IGNORE, contentValues2)

                    val contentValuesPHOTO2 = ContentValues()
                    contentValuesPHOTO2.put("id", 2)
                    contentValuesPHOTO2.put("id_property", 2)
                    contentValuesPHOTO2.put("image", "https://www.lamotte.fr/sites/default/files/assets/images/appartement-neuf-nantes-passage-saint-felix.jpg")
                    contentValuesPHOTO2.put("description", "Appartement extérieur")

                    db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO2)


                    val contentValuesPHOTO3 = ContentValues()
                    contentValuesPHOTO3.put("id", 3)
                    contentValuesPHOTO3.put("id_property", 2)
                    contentValuesPHOTO3.put("image", "https://s-ec.bstatic.com/images/hotel/max1024x768/626/62668201.jpg")
                    contentValuesPHOTO3.put("description", "Appartement intérieur")


                    db.insert("Image_property", OnConflictStrategy.IGNORE, contentValuesPHOTO3)

                }
            }
        }
    }
}
