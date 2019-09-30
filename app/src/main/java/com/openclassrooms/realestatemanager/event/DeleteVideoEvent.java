package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Video_property;

public class DeleteVideoEvent {

    public Video_property video;


    public DeleteVideoEvent(Video_property video) {

        this.video = video;


    }
}
