package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

public class DetailEvent {

    public Property property;


    public DetailEvent(Property property) {

        this.property = property;


    }
}
