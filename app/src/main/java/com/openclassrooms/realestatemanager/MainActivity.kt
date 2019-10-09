package com.openclassrooms.realestatemanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.os.Parcel
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.convertDollarToEuro
import com.openclassrooms.realestatemanager.utils.Utils.convertEuroToDollar
import com.openclassrooms.realestatemanager.utils.Utils.getTodayDate
import com.openclassrooms.realestatemanager.fragment.*

import com.openclassrooms.realestatemanager.database.injections.Injection
import com.openclassrooms.realestatemanager.database.todolist.PropertyViewModel
import com.openclassrooms.realestatemanager.event.*
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Video_property
import android.net.Uri
import com.openclassrooms.realestatemanager.utils.toNewDateFormat


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity() : AppCompatActivity(), LocationListener{

    override fun onProviderEnabled(p0: String?) {}
    override fun onProviderDisabled(p0: String?) {}
    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    override fun onLocationChanged(p0: Location?) {
        //remove location callback:
        locationManager.removeUpdates(this)

        latitude = p0!!.getLatitude()
        longitude = p0.getLongitude()
    }

    internal var listFragment = ListFragment()
    private lateinit var lastProperty : Property
    var fragmentManager = supportFragmentManager
    private val PERMISSION_REQUEST_LOCATION = 0
    private var mLayout: View? = null
    var latitude = 0.0
    var longitude = 0.0
    lateinit var locationManager: LocationManager
    lateinit var criteria: Criteria
    lateinit var bestProvider: String
    var tabletSize = false
    private val firstFragment = MapFragment()
    private var propertyViewModel: PropertyViewModel? = null
    var properties = ArrayList<Property>()
    var videos = ArrayList<Video_property>()
    var images = ArrayList<Image_property>()
    lateinit var button :FloatingActionButton

    companion object {

        val ID = "id"
        val VIDEOS = "Videos"
        val LONG = "long"
        val LAT = "lat"
        val IMAGES = "Image"
        val PROPERTIES = "properties"
        val PROPERTY = "property"
        val CREATEORMODIFY = "CreateOrModify"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        mLayout = findViewById(R.id.mlayout)

        tabletSize = resources.getBoolean(R.bool.isTablet)


        getSupportActionBar()?.setTitle(  getTodayDate);

        possibilityToOpenMap()

        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            longitude = location.getLongitude()
            latitude = location.getLatitude()
            fragmentManager = supportFragmentManager
        } else {
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            criteria = Criteria()
            bestProvider = locationManager.getBestProvider(criteria, true).toString()
            Toast.makeText(this, getString(R.string.location_internet), Toast.LENGTH_SHORT).show()
            locationManager.requestLocationUpdates(bestProvider, 1000, 0f, this)
        }

        var path = Uri.parse("https://www.ledrein-courgeon.fr/wp-content/uploads/2015/11/littre-facade-3.jpg");
        var appart =  Property( 786,"Appartement", 70000, 3, 1, 135, 4, "belle maison", "Villeneuve d'Ascq", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq", "école, métro", "à vendre", "26/06/1999".toNewDateFormat(), "02-01-0000".toNewDateFormat(), "Denis", "Euro", 2, 1);
        var video = Video_property(0, 786,"https://www.youtube.com/watch?v=Vg729rnWsm0")
        var image = Image_property(0,786, path.toString() , "chambre")
        var image2 = Image_property(0,786, path.toString(), "chambre")


        properties.add(appart)
        videos.add(video)
        images.add(image)
        images.add(image2)

        configureViewModel()
        getProperties()
        getVideos()
        getImages()

        lastProperty = properties.get(0);



        val args = Bundle()
        args.putSerializable(PROPERTIES, properties)
        args.putSerializable(IMAGES, images)
        listFragment.setArguments(args)

        fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit()

        button = findViewById(R.id.fab) as FloatingActionButton

        button.setVisibility(GONE)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                getProperties()
            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val i1 = item.itemId
        when (i1) {
            R.id.mapItem -> {

                if (Utils.haveInternetConnection(this)) {

                    val args = Bundle()
                    args.putDouble(LAT, latitude)
                    args.putDouble(LONG, longitude)
                    args.putSerializable(PROPERTIES, properties)
                    args.putSerializable(VIDEOS, videos)
                    args.putSerializable(IMAGES, images)
                    firstFragment.setArguments(args)

                    if (tabletSize) {
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, firstFragment).commit()
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.content_frame, firstFragment).commit()
                        button.setVisibility(VISIBLE)
                    }

                }else{
                    Toast.makeText(this, "Besoin d'une connexion internet pour afficher la carte.", Toast.LENGTH_SHORT).show()

                }


                return true
            }
            R.id.costItem-> {

                for (property in properties){
                    if (property.priceIsDollar == "Dollar") {
                        property.price = convertEuroToDollar(property.price)
                        property.priceIsDollar =  "Euro"
                        item.setIcon(R.drawable.ic_euro)
                    }else{
                        property.price = convertDollarToEuro(property.price)
                        property.priceIsDollar =  "Dollar"
                        item.setIcon(R.drawable.ic_dollar)
                    }
                }

                var listFragment2 = ListFragment()
                val args = Bundle()
                args.putSerializable(PROPERTIES, properties)
                args.putSerializable(IMAGES, images)
                listFragment2.setArguments(args)
                fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment2).commit()

                Toast.makeText(this, "La monnaie à bien été converti", Toast.LENGTH_SHORT).show()

                button.setVisibility(GONE)

                return true

            }
            R.id.modifyItem -> {

                val dialogFragment = MyAddFragment()
                dialogFragment.show(fragmentManager, "Sample Fragment")

                val args = Bundle()
                args.putParcelable(CREATEORMODIFY, lastProperty )
                args.putSerializable(VIDEOS, videos)
                args.putSerializable(IMAGES, images)

                dialogFragment.arguments = args

                return true

            }
            R.id.addItem -> {

                val dialogFragment = MyAddFragment()
                dialogFragment.show(fragmentManager, "Sample Fragment")

                return true

            }

            R.id.researchItem -> {

                val researchFragment = MyResearchFragment()
                researchFragment.show(fragmentManager, "Sample Fragment")

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe
    fun onDetail(event: DetailEvent) {

        val detailFragment = DetailFragment()
        // Supply index input as an argument.
        val args = Bundle()
        args.putParcelable(PROPERTY, event.property)
        args.putSerializable(PROPERTIES, properties)
        args.putSerializable(VIDEOS, videos)
        args.putSerializable(IMAGES, images)
        detailFragment.setArguments(args)

        lastProperty = event.property


        if (tabletSize) {
            fragmentManager.beginTransaction().replace(R.id.content_frame2, detailFragment).commit()
        } else {
            fragmentManager.beginTransaction().replace(R.id.content_frame, detailFragment).commit()
            button.setVisibility(VISIBLE)
        }

    }

    @Subscribe
    fun onAdd(event: AddEvent) {

        propertyViewModel!!.createProperty(event.property)
        getProperties()
    }

    @Subscribe
    fun onAddVideo(event: AddVideoEvent) {

        propertyViewModel!!.createVideo(event.video)
        getVideos()
    }

    @Subscribe
    fun onAddImage(event: AddImageEvent) {

        propertyViewModel!!.createImage(event.image)
        getImages()
    }

    @Subscribe
    fun onDeleteVideo(event: DeleteVideoEvent) {

        propertyViewModel!!.deleteVideo(event.video.id)
        getVideos()
    }

    @Subscribe
    fun onDeleteImage(event: DeleteImageEvent) {

        propertyViewModel!!.deleteImage(event.imageId)
        getImages()
    }

    @Subscribe
    fun onSearch(event: SearchEvent) {
        propertyViewModel!!.findCorrectProperties(event.type,event.priceMin, event.surfaceMin, event.pieceMin,
                event.priceMax,  event.surfaceMax,  event.pieceMax, event.descript,  event.ville,
                event.address,  event.proximity, event.statu,  event.startDate, event.sellingDate, event.agent, event.isDollar,
                event.photoMin, event.photoMax, event.videoMin, event.videoMax)
                .observe(this, Observer<List<Property>> {  listProperty: List<Property> -> updatePropertiesList2(listProperty)} )

    }

    @Subscribe
    fun onModify(event: ModifyEvent) {

        for (  property  in properties ){
            if (property.id == event.property.id){

                propertyViewModel!!.createProperty(event.property)
            }
        }
        getProperties()
    }


    // 3 - Get all tasks
    private fun getProperties() {
        propertyViewModel!!.properties.observe(this, Observer<List<Property>> {  listProperty: List<Property> -> updatePropertiesList(listProperty)} )
    }

    private fun updatePropertiesList2(listProperty: List<Property>) {

        properties.clear()
        for (property in listProperty as ArrayList<Property>){
            properties.add(property)
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment).commit()
        listFragment.adapter.updateData(properties)
        button.setVisibility(VISIBLE)
    }

    private fun updatePropertiesList(listProperty: List<Property>) {

        properties.clear()
        for (property in listProperty as ArrayList<Property>){
            properties.add(property)
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment).commit()
        listFragment.adapter.updateData(properties)
        button.setVisibility(GONE)
    }

    private fun getVideos() {
        propertyViewModel!!.videos.observe(this, Observer<List<Video_property>> {  listVideo: List<Video_property> -> updateVideosList(listVideo)} )
    }

    private fun updateVideosList(listVideos: List<Video_property>) {

        videos.clear()
        for (video in listVideos as ArrayList<Video_property>){
            videos.add(video)
        }
    }

    private fun getImages() {
        propertyViewModel!!.images.observe(this, Observer<List<Image_property>> {  listImage: List<Image_property> -> updateImagesList(listImage)} )
    }

    private fun updateImagesList(listImage: List<Image_property>) {

        images.clear()
        for (image in listImage as ArrayList<Image_property>){
            images.add(image)
        }


    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel::class.java)
        this.propertyViewModel!!.init()

    }

    fun possibilityToOpenMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, show restaurants
            Snackbar.make(mLayout!!,
                    "Accès à la localisation disponible", Snackbar.LENGTH_SHORT).show()
        } else {// Permission is missing and must be requested.
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for location permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start preview Activity.
                recreate()

                Snackbar.make(mLayout!!, "localisation autorisé", Snackbar.LENGTH_SHORT).show()
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout!!, "localisation refusé", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(mLayout!!, "la permission pour la localisation est nécessaire pour afficher la carte",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK") {
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
            }.show()
        } else {
            Snackbar.make(mLayout!!, "Autorisez vous l'accès à la localisation ?", Snackbar.LENGTH_SHORT).show()
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_LOCATION)
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}



