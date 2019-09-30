package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.Video_property;

public class AddVideoEvent {

    public Video_property video;


    public AddVideoEvent(Video_property video) {

        this.video = video;


    }
}
