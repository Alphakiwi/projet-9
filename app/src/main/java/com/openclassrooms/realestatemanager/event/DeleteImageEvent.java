package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

public class DeleteImageEvent {

    public int imageId;


    public DeleteImageEvent(int  imageId) {

        this.imageId = imageId;


    }
}
