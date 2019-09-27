package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

public class ModifyEvent {

    public Property property;


    public ModifyEvent(Property property) {

        this.property = property;


    }
}
