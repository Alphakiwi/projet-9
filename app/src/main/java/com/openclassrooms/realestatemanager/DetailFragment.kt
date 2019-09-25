package com.openclassrooms.realestatemanager

import android.content.Context
import android.graphics.Bitmap
import android.location.Criteria
import android.location.LocationManager
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Gallery
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.pm.PackageManager
import android.location.Geocoder

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

import java.util.ArrayList
import java.util.Arrays
import java.util.Date
import java.util.Vector


class DetailFragment : Fragment() {

    internal lateinit var myView: View
    private var mContext: Context? = null

    internal lateinit var recyclerView: RecyclerView
    var latitude = 0.0
    var longitude = 0.0
    private val firstFragment = MapFragment()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_detail, container, false)

        var fragmentManager = getChildFragmentManager()


        val args: Bundle? = arguments
        val property = args!!.getParcelable<Parcelable>("property") as Property?
        val properties = args.getSerializable("properties") as ArrayList<Property>?

        if (Geocoder.isPresent()) {
            try {
                val location = property!!.address
                val gc = Geocoder(mContext)
                val addresses = gc.getFromLocationName(location, 5) // get the found Address Objects

                val ll = ArrayList<LatLng>(addresses.size) // A list to save the coordinates if they are available
                for (a in addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll.add(LatLng(a.latitude, a.longitude))
                    }
                }

                if (ll.size>0) {

                    val args2 = Bundle()
                    args2.putDouble("lat", ll.get(0).latitude)
                    args2.putDouble("long", ll.get(0).longitude)
                    args2.putSerializable("properties", properties)
                    firstFragment.setArguments(args2)
                    fragmentManager!!.beginTransaction().replace(R.id.content_frame, firstFragment).commit()
                }




            } catch (e: IOException) {
                // handle the exception
            }

        }




        var soldate = " "
        if (property!!.selling_date != null) {
            soldate += property.selling_date
        }

        val description = myView.findViewById<TextView>(R.id.description)
        description.text = " " + property.description + "\n"
        val type = myView.findViewById<TextView>(R.id.type)
        type.text = type.text.toString() + property.type
        val money = myView.findViewById<TextView>(R.id.money)
        val surface = myView.findViewById<TextView>(R.id.surface)
        surface.text = surface.text.toString() + " " + property.surface
        val home = myView.findViewById<TextView>(R.id.home)
        home.text = home.text.toString() + " " + property.nb_piece
        val bed = myView.findViewById<TextView>(R.id.bed)
        bed.text = bed.text.toString() + " " + property.nb_bedroom
        val bath = myView.findViewById<TextView>(R.id.bath)
        bath.text = bath.text.toString() + " " + property.nb_bathroom
        val location = myView.findViewById<TextView>(R.id.location)
        location.text = location.text.toString() + property.address
        val interest = myView.findViewById<TextView>(R.id.interest)
        interest.text = interest.text.toString() + property.proximity
        val start_date = myView.findViewById<TextView>(R.id.start_date)
        start_date.text = start_date.text.toString() + " " + property.start_date
        val status = myView.findViewById<TextView>(R.id.status)
        status.text = status.text.toString() + " " + property.status + soldate
        val agent = myView.findViewById<TextView>(R.id.agent)
        agent.text = agent.text.toString() + property.estate_agent

        val sold = myView.findViewById<ImageView>(R.id.sold)

        val comparison = property.status.compareTo("vendu")

        if (comparison != 0) {
            sold.visibility = View.GONE
        }

        if (property.priceIsDollar === "Dollar") {
            money.text = money.text.toString() + "$ " + property.price.toString()
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dollar, 0, 0, 0)


        } else {
            money.text = money.text.toString() + property.price.toString() + " €"
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_euro, 0, 0, 0)
        }


        val gallery = myView.findViewById<View>(R.id.gallery1) as Gallery
        gallery.adapter = ImageAdapter(mContext!!, property.photo)
        val imageView = myView.findViewById<View>(R.id.imageAvatar) as ImageView

        Glide.with(mContext!!)
                .load(property.photo[0].image)
                //.load("https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg")
                .into(imageView)

        gallery.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            Toast.makeText(mContext, property.photo[position].descript,
                    Toast.LENGTH_SHORT).show()
            // display the images selected
            Glide.with(mContext!!)
                    .load(property.photo[position].image)
                    .into(imageView)
        }




        if (property.video != null) {
            recyclerView = myView.findViewById<View>(R.id.recyclerView) as RecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(mContext)
            val videoAdapter = VideoAdapter(property.video!!)
            recyclerView.adapter = videoAdapter
        }





        return myView

    }

}
