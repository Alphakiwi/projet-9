


package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import androidx.room.ColumnInfo




@Entity
data class Property (@PrimaryKey(autoGenerate = true) var id : Int, var type : String, var price : Int, var nb_bedroom : Int, var nb_bathroom : Int,
                     var surface : Int, var nb_piece : Int, var description : String, var photo : String,
                     var video : String?, var ville : String, var address : String, var proximity : String,
                     var status : String, var start_date: String, var selling_date : String?, var estate_agent : String, var priceIsDollar : String) : Parcelable {
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
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
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
        parcel.writeString(photo)
        parcel.writeString(ville)
        parcel.writeString(address)
        parcel.writeString(proximity)
        parcel.writeString(status)
        parcel.writeString(start_date)
        parcel.writeString(selling_date)
        parcel.writeString(estate_agent)
        parcel.writeString(priceIsDollar)
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
    }

}

class Image_property (var image : String, var descript : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(descript)
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
    }






}


