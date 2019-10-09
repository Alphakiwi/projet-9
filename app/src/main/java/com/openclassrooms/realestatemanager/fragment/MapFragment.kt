package com.openclassrooms.realestatemanager.fragment


import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.MainActivity.Companion.IMAGES
import com.openclassrooms.realestatemanager.MainActivity.Companion.LAT
import com.openclassrooms.realestatemanager.MainActivity.Companion.LONG
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTIES
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTY
import com.openclassrooms.realestatemanager.MainActivity.Companion.VIDEOS
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Video_property
import java.io.IOException

import java.util.ArrayList


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private var mMap: GoogleMap? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    private var mContext: Context? = null

    private var mView: View? = null

    private var lng: Double? = null
    private var lat: Double? = null
    private var properties: ArrayList<Property>? = null
    var videos : ArrayList<Video_property>? = null
    var images : ArrayList<Image_property>? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this com.openclassrooms.realestatemanager.fragment
        mView = inflater.inflate(R.layout.map_layout, container, false)

        val args = arguments
        lat = args!!.getDouble(LAT)
        lng = args.getDouble(LONG)
        properties = args.getSerializable(PROPERTIES) as ArrayList<Property>
        videos = args.getSerializable(VIDEOS) as ArrayList<Video_property>?
        images = args.getSerializable(IMAGES) as ArrayList<Image_property>?


        val button = mView!!.findViewById<View>(R.id.recentrer) as FloatingActionButton
        button.setOnClickListener(View.OnClickListener {
            if (ActivityCompat.checkSelfPermission(mContext!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext!!,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return@OnClickListener
            }
            val here = LatLng(lat!!, lng!!)

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14f))
        })

        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Get location
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(mContext!!)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()

            mGoogleApiClient!!.connect()
        }


        mMap!!.setOnInfoWindowClickListener { marker ->

            val name = marker.title


            val comp = name.compareTo(getString(R.string.here))

            if (comp != 0) {

            val snippet = marker.snippet
            val separated = snippet.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()


            var propertyThis = properties!![0]



                val detailFragment = DetailFragment()
                // Supply index input as an argument.
                val args = Bundle()

                for ( property in properties!!) {
                    if(property.id == separated[1].toInt()){
                        propertyThis = property
                    }
                }
                args.putParcelable(PROPERTY, propertyThis)
                args.putSerializable(PROPERTIES, properties)
                args.putSerializable(VIDEOS, videos)
                args.putSerializable(IMAGES, images)
                detailFragment.setArguments(args)


                fragmentManager!!.beginTransaction().replace(R.id.content_frame, detailFragment).commit()
            }
        }
    }


    override fun onStart() {
        if (mGoogleApiClient != null)
            mGoogleApiClient!!.connect()
        super.onStart()
    }

    override fun onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient!!.disconnect()
        super.onStop()
    }

    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(mContext!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(mContext!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val here = LatLng(lat!!, lng!!)


        mMap!!.addMarker(MarkerOptions().position(here).title(getString(R.string.here)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

       for ( property in properties!!) {

           if (Geocoder.isPresent()) {
               try {
                   val location = property.address
                   val gc = Geocoder(mContext)
                   val addresses = gc.getFromLocationName(location, 5) // get the found Address Objects

                   val ll = ArrayList<LatLng>(addresses.size) // A list to save the coordinates if they are available
                   for (a in addresses) {
                       if (a.hasLatitude() && a.hasLongitude()) {
                           ll.add(LatLng(a.latitude, a.longitude))
                       }
                   }

                   if (ll.size>0) {
                       mMap!!.addMarker(MarkerOptions().position(ll.get(0))
                               .title(property.type)
                               .snippet(property.address + "\n :" + property.id)
                               .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                   }



               } catch (e: IOException) {
                   // handle the exception
               }

           }

        }
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14f))


    }


    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

}