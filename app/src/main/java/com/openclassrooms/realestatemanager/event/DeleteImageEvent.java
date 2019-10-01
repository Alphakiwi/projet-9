package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

public class DeleteImageEvent {

    public Image_property image;


    public DeleteImageEvent(Image_property image) {

        this.image = image;


    }
}
