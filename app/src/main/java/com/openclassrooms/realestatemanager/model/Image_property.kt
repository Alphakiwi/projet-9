package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = arrayOf(ForeignKey(entity = Property::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_property"))))
class Image_property (@PrimaryKey(autoGenerate = true) var id : Int, var id_property: Int, var image : ByteArray, var description : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.createByteArray()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_property)
        parcel.writeByteArray(image)
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
    }

}