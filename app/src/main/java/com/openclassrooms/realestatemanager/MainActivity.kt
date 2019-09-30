package com.openclassrooms.realestatemanager

import android.Manifest
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
import com.openclassrooms.realestatemanager.model.Video_property


class MainActivity() : AppCompatActivity(), LocationListener{

    override fun onProviderEnabled(p0: String?) {}
    override fun onProviderDisabled(p0: String?) {}
    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    override fun onLocationChanged(p0: Location?) {
        //remove location callback:
        locationManager.removeUpdates(this)

        latitude = p0!!.getLatitude()
        longitude = p0!!.getLongitude()
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

    lateinit var button :FloatingActionButton


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
            longitude = location!!.getLongitude()
            latitude = location!!.getLatitude()
            fragmentManager = supportFragmentManager
        } else {
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            criteria = Criteria()
            bestProvider = locationManager.getBestProvider(criteria, true).toString()
            Toast.makeText(this, "Le chargement de la localisation peut prendre plus ou moins de temps en fonction de votre connexion internet.", Toast.LENGTH_SHORT).show()
            locationManager.requestLocationUpdates(bestProvider, 1000, 0f, this)
        }

        var appart =  Property(2, "Appartement", 70000, 3, 1, 135, 4, "belle maison", "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg", "Villeneuve d'Ascq", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq", "école, métro", "à vendre", "26/06/1999", null, "Denis", "Euro");
        var video = Video_property(1,2,"https://www.youtube.com/watch?v=Vg729rnWsm0")

        properties.add(appart)
        videos.add(video)

        configureViewModel()
        getProperties()
        getVideos()

        lastProperty = properties.get(0);



        val args = Bundle()
        args.putSerializable("properties", properties)
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
                    args.putDouble("lat", latitude)
                    args.putDouble("long", longitude)
                    args.putSerializable("properties", properties)
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
                args.putSerializable("properties", properties)
                listFragment2.setArguments(args)
                fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment2).commit()

                Toast.makeText(this, "La monnaie à bien été converti", Toast.LENGTH_SHORT).show()

                button.setVisibility(GONE)

                return true

            }
            R.id.modifyItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyAddFragment()
                dialogFragment.show(fm, "Sample Fragment")

                val args = Bundle()
                args.putParcelable("CreateOrModify", lastProperty )
                args.putSerializable("Videos", videos)
                dialogFragment.arguments = args

                return true

            }
            R.id.addItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyAddFragment()
                dialogFragment.show(fm, "Sample Fragment")

                return true

            }

            R.id.researchItem -> {

                val fm = getFragmentManager()
                val researchFragment = MyResearchFragment()

                val args = Bundle()
                args.putSerializable("properties", ArrayList(properties))
                researchFragment.arguments = args
                researchFragment.show(fm, "Sample Fragment")

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
        args.putParcelable("property", event.property)
        args.putSerializable("properties", properties)
        args.putSerializable("Videos", videos)
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
    fun onDeleteVideo(event: DeleteVideoEvent) {

        propertyViewModel!!.deleteVideo(event.video.id)
        getVideos()
    }


 /*   @Subscribe
    fun onSearch(event: SearchEvent) {
        propertyViewModel!!.findCorrectProperties(event.type,event.priceMin, event.bedMin, event.bathMin, event.surfaceMin, event.pieceMin,
                event.priceMax, event.bedMax,  event.bathMax,  event.surfaceMax,  event.pieceMax, event.descript,  event.ville,
                event.address,  event.proximity, event.statu,  event.startDate, event.sellingDate, event.agent, event.isDollar)
                .observe(this, Observer<List<Property>> {  listProperty: List<Property> -> updatePropertiesList2(listProperty)} )

    }*/

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



