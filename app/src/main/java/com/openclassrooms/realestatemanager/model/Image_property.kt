package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*


@Entity(indices = [Index("id_property")],
        foreignKeys = arrayOf(ForeignKey(entity = Property::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_property"))))

class Image_property (@PrimaryKey(autoGenerate = true) var id : Int, var id_property: Int
                      , var image : String, var description : String) : Parcelable {



    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_property)
        parcel.writeString(image)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<Image_property> {
        override fun createFromParcel(parcel: Parcel): Image_property {
            return Image_property(parcel)
        }

        override fun newArray(size: Int): Array<Image_property?> {
            return arrayOfNulls(size)
        }

        fun fromContentValues(values: ContentValues): Image_property {
            val image_property = Image_property(0, 0, "", "")
            if (values.containsKey("id")) image_property.id = values.getAsInteger("id")
            if (values.containsKey("id_property")) image_property.id_property = values.getAsInteger("id_property")
            if (values.containsKey("image")) image_property.image = values.getAsString("image")
            if (values.containsKey("description")) image_property.description = values.getAsString("description")


            return image_property
        }
    }

}