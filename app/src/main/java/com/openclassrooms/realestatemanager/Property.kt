


package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

data class Property (var id : Int, var type : String, var price : Int, var nb_bedroom : Int, var nb_bathroom : Int,
                     var surface : Int, var nb_piece : Int, var description : String, var photo : List<Image_property>,
                     var video : Vector<YouTubeVideos>?, var ville : String, var address : String, var proximity : List<String>,
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
            TODO("photo"),
            TODO("video"),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.createStringArrayList()!!,
            parcel.readString()!!,
            TODO("start_date"),
            TODO("selling_date"),
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
        parcel.writeString(ville)
        parcel.writeString(address)
        parcel.writeStringList(proximity)
        parcel.writeString(status)
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

class Image_property (var image : ByteArray, var descript : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.createByteArray()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByteArray(image)
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


