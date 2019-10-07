package com.openclassrooms.realestatemanager.event

import com.openclassrooms.realestatemanager.model.Property

import java.util.ArrayList

class SearchEvent(var type: String, var priceMin: Int, var surfaceMin: Int, var pieceMin: Int, var priceMax: Int, var surfaceMax: Int, var pieceMax: Int, var descript: String, var ville: String, var address: String, var proximity: String, var statu: String, var startDate: String, var sellingDate: String, var agent: String, var isDollar: String, var photoMin: Int, var photoMax: Int, var videoMin: Int, var videoMax: Int)
