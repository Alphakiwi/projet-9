


package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Property (@PrimaryKey(autoGenerate = true) var id : Int, var type : String, var price : Int, var nb_bedroom : Int, var nb_bathroom : Int,
                     var surface : Int, var nb_piece : Int, var description : String, var ville : String, var address : String, var proximity : String,
                     var status : String, var start_date: String, var selling_date : String?, var estate_agent : String, var priceIsDollar : String, var nb_photo : Int, var nb_video : Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,     parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeInt(price)
        parcel.writeInt(nb_bedroom)
        parcel.writeInt(nb_bathroom)
        parcel.writeInt(surface)
        parcel.writeInt(nb_piece)
        parcel.writeString(description)
        parcel.writeString(ville)
        parcel.writeString(address)
        parcel.writeString(proximity)
        parcel.writeString(status)
        parcel.writeString(start_date)
        parcel.writeString(selling_date)
        parcel.writeString(estate_agent)
        parcel.writeString(priceIsDollar)
        parcel.writeInt(nb_photo)
        parcel.writeInt(nb_video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }

        fun fromContentValues(values: ContentValues): Property {
            val property = Property(0, "",0,0,0, 0,0, "", "", "","","","","","","", 0,0)
            if (values.containsKey("id")) property.id = values.getAsInteger("id")
            if (values.containsKey("type"))property.type = values.getAsString("type")
            if (values.containsKey("price")) property.price = values.getAsInteger("price")
            if (values.containsKey("nb_bedroom")) property.nb_bedroom = values.getAsInteger("nb_bedroom")
            if (values.containsKey("nb_bathroom")) property.nb_bathroom = values.getAsInteger("nb_bathroom")
            if (values.containsKey("surface")) property.surface = values.getAsInteger("surface")
            if (values.containsKey("nb_piece")) property.nb_piece = values.getAsInteger("nb_piece")
            if (values.containsKey("description")) property.description = values.getAsString("description")
            if (values.containsKey("ville"))property.ville = values.getAsString("ville")
            if (values.containsKey("address")) property.address = values.getAsString("address")
            if (values.containsKey("proximity"))property.proximity = values.getAsString("proximity")
            if (values.containsKey("status")) property.status = values.getAsString("status")
            if (values.containsKey("start_date")) property.start_date = values.getAsString("start_date")
            if (values.containsKey("selling_date"))property.selling_date = values.getAsString("selling_date")
            if (values.containsKey("estate_agent")) property.estate_agent = values.getAsString("estate_agent")
            if (values.containsKey("priceIsDollar")) property.priceIsDollar = values.getAsString("priceIsDollar")
            if (values.containsKey("nb_photo")) property.nb_photo = values.getAsInteger("nb_photo")
            if (values.containsKey("nb_video")) property.nb_video= values.getAsInteger("nb_video")

            return property
        }
    }



}




