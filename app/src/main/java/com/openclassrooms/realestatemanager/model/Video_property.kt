


package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import androidx.room.ColumnInfo
import androidx.room.ForeignKey





@Entity(foreignKeys = arrayOf(ForeignKey(entity = Property::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_property"))))
class Video_property (@PrimaryKey(autoGenerate = true) var id : Int, var id_property: Int, var video : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_property)
        parcel.writeString(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video_property> {
        override fun createFromParcel(parcel: Parcel): Video_property {
            return Video_property(parcel)
        }

        override fun newArray(size: Int): Array<Video_property?> {
            return arrayOfNulls(size)
        }
    }

}


