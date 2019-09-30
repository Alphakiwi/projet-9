package com.openclassrooms.realestatemanager.event;

import com.openclassrooms.realestatemanager.model.Property;

import java.util.ArrayList;

public class SearchEvent {

    public String type;
    public String priceMin;
    public String bedMin;
    public String bathMin;
    public String surfaceMin;
    public String pieceMin;
    public String priceMax;
    public String bedMax;
    public String bathMax;
    public String surfaceMax;
    public String pieceMax;
    public String descript;
    public String ville;
    public String address;
    public String proximity;
    public String statu;
    public String startDate;
    public String sellingDate;
    public String agent;
    public String isDollar;



    public SearchEvent( String type, String priceMin, String bedMin, String bathMin, String surfaceMin, String pieceMin, String priceMax, String bedMax, String bathMax, String surfaceMax, String pieceMax, String descript, String ville, String address, String proximity, String statu, String startDate, String sellingDate, String agent, String isDollar) {

        this.type        = type           ;
        this.priceMin    = priceMin   ;
        this.bedMin      =   bedMin     ;
        this.bathMin     =   bathMin    ;
        this.surfaceMin  =    surfaceMin ;
        this.pieceMin    =    pieceMin   ;
        this.priceMax    =    priceMax   ;
        this.bedMax      =   bedMax     ;
        this.bathMax     =   bathMax    ;
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



    }
}
