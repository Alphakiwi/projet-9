package com.openclassrooms.realestatemanager.utils



fun String.toVideoUrl() =  if (this.length>42) "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
        this.substring(32,43) +  "\" frameborder=\"0\" allowfullscreen></iframe>" else this

//youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IdvFBL4Kalo\" frameborder=\"0\" allowfullscreen></iframe>") );
