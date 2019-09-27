package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

public class AddEvent {

    public Property property;


    public AddEvent(Property property) {

        this.property = property;


    }
}
