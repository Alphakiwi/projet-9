package com.openclassrooms.realestatemanager.utils

import java.text.ParseException
import java.text.SimpleDateFormat


fun String.toVideoUrl() =  if (this.length>42) "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
        this.substring(32,43) +  "\" frameborder=\"0\" allowfullscreen></iframe>" else this

fun String.toNewDateFormat() =  if (this.length==10) this.substring(6,10) + '-' + this.substring(3,5) + '-' + this.substring(0,2) else this


