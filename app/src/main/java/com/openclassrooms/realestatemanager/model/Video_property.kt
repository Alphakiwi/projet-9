


package com.openclassrooms.realestatemanager.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import android.content.ContentValues



@SuppressLint("ParcelCreator")
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


    companion object {
        fun fromContentValues(values: ContentValues): Video_property {
            val video_property = Video_property(0, 0, "")
            if (values.containsKey("id")) video_property.id = values.getAsInteger("id")
            if (values.containsKey("id_property")) video_property.id_property = values.getAsInteger("id_property")
            if (values.containsKey("video")) video_property.video = values.getAsString("video")

            return video_property
        }
    }


}


