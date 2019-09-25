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
import com.openclassrooms.realestatemanager.Utils.convertDollarToEuro
import com.openclassrooms.realestatemanager.Utils.convertEuroToDollar
import com.openclassrooms.realestatemanager.Utils.getTodayDate

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import android.os.Parcel
import android.os.Parcelable
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


class MainActivity() : AppCompatActivity(), LocationListener, Parcelable {
    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(p0: Location?) {
        //remove location callback:
        locationManager.removeUpdates(this)

        //open the map:
        latitude = p0!!.getLatitude()
        longitude = p0!!.getLongitude()
    }

    internal var listFragment = ListFragment()
    val properties = ArrayList<Property>()
    private lateinit var lastProperty : Property
    var fragmentManager = supportFragmentManager
    private val PERMISSION_REQUEST_LOCATION = 0
    private var mLayout: View? = null

    var latitude = 0.0
    var longitude = 0.0
    lateinit var locationManager: LocationManager
    lateinit var criteria: Criteria
    lateinit var bestProvider: String
    private val firstFragment = MapFragment()


    constructor(parcel: Parcel) : this() {
        lastProperty = parcel.readParcelable(Property::class.java.classLoader)!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        mLayout = findViewById(R.id.mlayout)


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



        val youtubeVideos = Vector<YouTubeVideos>()

        youtubeVideos.add(YouTubeVideos("https://www.youtube.com/watch?v=IdvFBL4Kalo"))
        youtubeVideos.add(YouTubeVideos("https://www.youtube.com/watch?v=Vg729rnWsm0"))


        //youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IdvFBL4Kalo\" frameborder=\"0\" allowfullscreen></iframe>") );
        /* youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/sdUUx5FdySs\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/91SsmqV47yQ\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i_Q22nDXfq8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mtDokmIKRp4\" frameborder=\"0\" allowfullscreen></iframe>") );
*/


        val couleurs = Arrays.asList(Image_property("https://www.maisons-ossature-bois-chalets-charpente-favre-felix.com/images/maison-bois/maison-bois-65.jpg",
                "Vue extérieur"), Image_property("https://listspirit.com/wp-content/uploads/2017/08/deco-salon-amenagement-interieur-moderne-dune-maison-au-canada.jpg", "Vue intérieur"),
                 Image_property(
                         "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg",
                         "Chambre"
                 ),Image_property(
                "http://www.bainsetsolutions.fr/scripts/files/5603e6f7554961.58606024/perspective-3d-1-renovation-salle-de-bain-st-gilles-bainsetsolutions-pace-rennes.jpg",
                "Salle de bain" ))
        val couleurs2 = Arrays.asList(Image_property("https://q-ec.bstatic.com/images/hotel/max1024x768/480/48069729.jpg", "descrip"))

        val property = Property(1, "Maison", 120000, 3, 1, 135, 4, "belle maison", couleurs, null, "Lille", "27 Rue Nationale, 59000 Lille", "école, métro", "vendu","26/06/1999", "28/06/1999", "Denis","Euro")
        val appart = Property(2, "Appartement", 70000, 3, 1, 135, 4, "belle maison", couleurs2, youtubeVideos, "Villeneuve d'Ascq", " 12 Rue du Président Paul Doumer, Villeneuve-d'Ascq", "école, métro", "à vendre", "26/06/1999", null, "Denis","Euro")

        properties.add(property)
        properties.add(appart)


        lastProperty = properties.get(0);


        val args = Bundle()
        args.putSerializable("properties", properties)
        listFragment.setArguments(args)

        fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit()

        val button = findViewById(R.id.fab) as FloatingActionButton


        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {



                fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit()


            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater

        //R.menu.menu est l'id de notre menu

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
                    fragmentManager.beginTransaction().replace(R.id.content_frame, firstFragment).commit()

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




                return true

            }
            R.id.modifyItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyDialogFragment()
                dialogFragment.show(fm, "Sample Fragment")

                val args = Bundle()
                args.putParcelable("CreateOrModify", lastProperty )
                dialogFragment.arguments = args
                return true



                return true

            }
            R.id.addItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyDialogFragment()
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
    fun onActualise(event: ActualiseEvent) {

        val detailFragment = DetailFragment()
        // Supply index input as an argument.
        val args = Bundle()
        args.putParcelable("property", event.property)
        args.putSerializable("properties", properties)
        detailFragment.setArguments(args)


        fragmentManager.beginTransaction().replace(R.id.content_frame, detailFragment).commit()

        lastProperty = event.property

    }

    @Subscribe
    fun onAdd(event: AddEvent) {

        properties.add(event.property)
        listFragment.adapter.notifyDataSetChanged()


    }

    @Subscribe
    fun onSearch(event: SearchEvent) {

        var listFragment2 = ListFragment()
        val args = Bundle()
        args.putSerializable("properties", event.properties)
        listFragment2.setArguments(args)
        fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment2).commit()




    }

    @Subscribe
    fun onModify(event: ModifyEvent) {

        for (  property  in properties ){
            if (property.id == event.property.id){

                property.type = event.property.type
                property.price = event.property.price
                property.nb_bedroom = event.property.nb_bedroom
                property.nb_bathroom = event.property.nb_bathroom
                property.surface = event.property.surface
                property.nb_piece = event.property.nb_piece
                property.description = event.property.description
                property.photo = event.property.photo
                property.video = event.property.video
                property.ville = event.property.ville
                property.address= event.property.address
                property.proximity = event.property.proximity
                property.status = event.property.status
                property.start_date = event.property.start_date
                property.selling_date = event.property.selling_date
                property.estate_agent = event.property.estate_agent
                property.priceIsDollar = event.property.priceIsDollar
            }
        }

        listFragment.adapter.notifyDataSetChanged()

        var listFragment2 = ListFragment()
        val args = Bundle()
        args.putSerializable("properties", properties)
        listFragment2.setArguments(args)
        fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment2).commit()


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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(lastProperty, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }


}



