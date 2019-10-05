package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

import java.util.ArrayList;

public class SearchEvent {

    public String type;
    public int priceMin;
    public int surfaceMin;
    public int pieceMin;
    public int priceMax;
    public int surfaceMax;
    public int pieceMax;
    public String descript;
    public String ville;
    public String address;
    public String proximity;
    public String statu;
    public String startDate;
    public String sellingDate;
    public String agent;
    public String isDollar;
    public int photoMin;
    public int photoMax;
    public int videoMin;
    public int videoMax;



    public SearchEvent( String type, int priceMin, int surfaceMin, int pieceMin, int priceMax, int surfaceMax, int pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar, int photoMin,int photoMax, int videoMin, int videoMax) {

        this.type        = type           ;
        this.priceMin    = priceMin   ;
        this.surfaceMin  =    surfaceMin ;
        this.pieceMin    =    pieceMin   ;
        this.priceMax    =    priceMax   ;
        this.surfaceMax  =   surfaceMax ;
        this.pieceMax    =   pieceMax   ;
        this.descript    =   descript   ;
        this.ville       =   ville      ;
        this.address     =   address    ;
        this.proximity   =   proximity  ;
        this.statu       =   statu      ;
        this.startDate   =   startDate  ;
        this.sellingDate =   sellingDate;
        this.agent       =   agent      ;
        this.isDollar    =   isDollar   ;
        this.photoMin = photoMin;
        this.photoMax = photoMax;
        this.videoMin = videoMin;
        this.videoMax = videoMax;

    }
}
