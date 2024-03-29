@file:Suppress("DEPRECATION")

package com.openclassrooms.realestatemanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.location.Geocoder
import android.text.Html
import android.view.View.GONE
import android.widget.*

import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.MainActivity.Companion.IMAGES
import com.openclassrooms.realestatemanager.MainActivity.Companion.LAT
import com.openclassrooms.realestatemanager.MainActivity.Companion.LONG
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTIES
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTY
import com.openclassrooms.realestatemanager.MainActivity.Companion.VIDEOS
import com.openclassrooms.realestatemanager.adapter.ImageAdapter
import com.openclassrooms.realestatemanager.adapter.VideoAdapter
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Video_property
import java.io.IOException

import java.util.ArrayList
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.event.AddEvent
import com.openclassrooms.realestatemanager.event.LaunchModifyEvent
import com.openclassrooms.realestatemanager.event.ModifyEvent
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.toFrenchDateFormat
import com.openclassrooms.realestatemanager.utils.toNewDateFormat
import org.greenrobot.eventbus.EventBus

@Suppress("UNCHECKED_CAST")
class DetailFragment : Fragment() {

    internal lateinit var myView: View
    private var mContext: Context? = null

    internal lateinit var recyclerView: RecyclerView
    private val firstFragment = MapFragment()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_detail, container, false)

        val fragmentManager = getChildFragmentManager()


        val args: Bundle? = arguments
        val property = args!!.getParcelable(PROPERTY) as Property?
        val properties = args.getSerializable(PROPERTIES) as ArrayList<Property>?
        val videos = args.getSerializable(VIDEOS) as ArrayList<Video_property>?
        val images = args.getSerializable(IMAGES) as ArrayList<Image_property>?



        if (Geocoder.isPresent()) {
            try {
                val location = property!!.address
                val gc = Geocoder(mContext)
                val addresses = gc.
                        getFromLocationName(location, 5) // get the found Address Objects

                val ll = ArrayList<LatLng>(addresses.size) // A list to save the coordinates if they are available
                for (a in addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll.add(LatLng(a.latitude, a.longitude))
                    }
                }

                if (ll.size>0) {

                    val args2 = Bundle()
                    args2.putDouble(LAT, ll.get(0).latitude)
                    args2.putDouble(LONG, ll.get(0).longitude)
                    args2.putSerializable(PROPERTIES, properties)
                    args2.putSerializable(VIDEOS, videos)
                    args2.putSerializable(IMAGES, images)
                    firstFragment.setArguments(args2)
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, firstFragment)
                            .commit()
                }else{
                    myView.findViewById<FrameLayout>(R.id.content_frame).visibility = GONE

                }




            } catch (e: IOException) {
                // handle the exception
                myView.findViewById<FrameLayout>(R.id.content_frame).visibility = GONE
            }

        }




        var soldate = " "
        if (property!!.selling_date!!.compareTo(getString(R.string.date_default2).toNewDateFormat())!= 0) {
            soldate += property.selling_date!!.toFrenchDateFormat()
        }

        val description = myView.findViewById<TextView>(R.id.description)
        description.text = Html.fromHtml("<u><b> Description </b></u> : "  + property.description )
        val type = myView.findViewById<TextView>(R.id.type)
        type.text = Html.fromHtml("<u><b>" + type.text.toString() + "</b></u>" + " " + property.type )
        val money = myView.findViewById<TextView>(R.id.money)
        val surface = myView.findViewById<TextView>(R.id.surface)
        surface.text = Html.fromHtml("<u><b>" + surface.text.toString() + "</b></u>"+ " " + property.surface )
        val home = myView.findViewById<TextView>(R.id.home)
        home.text = Html.fromHtml("<u><b>" +  home.text.toString()+ "</b></u>" + " " + property.nb_piece)
        val bed = myView.findViewById<TextView>(R.id.bed)
        bed.text = Html.fromHtml("<u><b>" +  bed.text.toString() + "</b></u>"+ " " + property.nb_bedroom)
        val bath = myView.findViewById<TextView>(R.id.bath)
        bath.text = Html.fromHtml("<u><b>" +  bath.text.toString() + "</b></u>"+ " " + property.nb_bathroom)
        val location = myView.findViewById<TextView>(R.id.location)
        location.text = Html.fromHtml("<u><b>" +  location.text.toString()+ "</b></u>" + " " + property.address)
        val interest = myView.findViewById<TextView>(R.id.interest)
        interest.text = Html.fromHtml("<u><b>" +  interest.text.toString()+ "</b></u>" + " " + property.proximity)
        val start_date = myView.findViewById<TextView>(R.id.start_date)
        start_date.text = Html.fromHtml("<u><b>" + start_date.text.toString()+ "</b></u>" + " " + property.start_date.toFrenchDateFormat())
        val status = myView.findViewById<TextView>(R.id.status)
        status.text =  Html.fromHtml("<u><b>" + status.text.toString()+ "</b></u>" + " " + property.status + soldate)
        val agent = myView.findViewById<TextView>(R.id.agent)
        agent.text = Html.fromHtml("<u><b>" + agent.text.toString()+ "</b></u>" + " " + property.estate_agent)

        val sold = myView.findViewById<ImageView>(R.id.sold)

        val comparison = property.status.compareTo(getString(R.string.sold))

        if (comparison != 0) {
            sold.visibility = View.GONE
        }

        if (property.priceIsDollar === getString(R.string.dollar)) {
            money.text = Html.fromHtml("<u><b>" +  money.text.toString() + "</b></u>" + " "  + "$ " + property.price.toString())
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dollar, 0, 0, 0)


        } else {
            money.text = Html.fromHtml("<u><b>" +  money.text.toString() + "</b></u>" + " "  + property.price.toString() + " €")
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_euro, 0, 0, 0)
        }


        val listImages = ArrayList<String>()
        val listDescription = ArrayList<String>()


        if (images != null) {
            for (image in images) {
                if (property.id ==  image.id_property ) {
                    listImages.add(image.image)
                    listDescription.add(image.description)
                }
            }
        }

        if (listImages.size < 1){
            listImages.add("https://thumbs.dreamstime.com/b/house-question-mark-white-background-36739191.jpg")
            listDescription.add("ERROR")
        }

        @Suppress("DEPRECATION")
        val gallery = myView.findViewById<View>(R.id.gallery1) as Gallery
        gallery.adapter = ImageAdapter(mContext!!, listImages)
        val imageView = myView.findViewById<View>(R.id.imageAvatar) as ImageView



        Glide.with(mContext!!)
                .load(listImages[0])
                .into(imageView)

        gallery.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(mContext, listDescription.get(position),
                    Toast.LENGTH_SHORT).show()
            // display the images selected
            Glide.with(mContext!!)
                    .load(listImages.get(position))
                    .into(imageView)
        }

        if (Utils.haveInternetConnection(mContext!!)) {

            var listVideos = ArrayList<String>()

            if (videos != null) {
                for (video in videos) {
                    if (property.id == video.id_property) {
                        listVideos.add(video.video)
                    }
                }
            }

            recyclerView = myView.findViewById<View>(R.id.recyclerView) as RecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(mContext)
            val videoAdapter = VideoAdapter(listVideos)
            recyclerView.adapter = videoAdapter

        }


        val fab_modify = myView.findViewById<FloatingActionButton>(R.id.fab_modify)

        fab_modify.setOnClickListener {
            EventBus.getDefault().post(LaunchModifyEvent(property))
        }



        return myView

    }

}
