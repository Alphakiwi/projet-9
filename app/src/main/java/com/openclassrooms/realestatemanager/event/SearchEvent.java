package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

import java.util.ArrayList;

public class SearchEvent {

    public ArrayList<Property> properties;


    public SearchEvent(ArrayList<Property> properties) {

        this.properties = properties;


    }
}
