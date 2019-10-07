package com.openclassrooms.realestatemanager.utils



fun String.toVideoUrl() =  if (this.length>42) "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
        this.substring(32,43) +  "\" frameborder=\"0\" allowfullscreen></iframe>" else this

fun String.toNewDateFormat() =  if (this.substring(2,3).compareTo("/") == 0) this.substring(6,10) + '-' + this.substring(3,5) + '-' + this.substring(0,2) else this

fun String.toFrenchDateFormat() =  if (this.substring(4,5).compareTo("-") == 0) this.substring(8,10) + '/' + this.substring(5,7) + '/' + this.substring(0,4) else this

