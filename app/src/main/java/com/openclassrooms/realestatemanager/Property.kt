package com.openclassrooms.realestatemanager

import java.util.*

data class Property (var type : String, var price : Int,
                     var surface : Int, var nb_piece : Int, var description : String, var photo : List<Image_property>,
                     var video : Image_property?, var address : String, var proximity : List<String>,
                     var status : Boolean, var start_date: Date, var selling_date : Date?, var estate_agent : String )

class Image_property (var image : String, var descript : String)