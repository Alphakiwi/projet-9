package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Video_property;

public class AddImageEvent {

    public Image_property image;


    public AddImageEvent(Image_property image) {

        this.image = image;


    }
}
