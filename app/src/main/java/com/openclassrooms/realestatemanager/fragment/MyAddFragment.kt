package com.openclassrooms.realestatemanager.fragment

import androidx.fragment.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus
import java.util.*
import android.app.DatePickerDialog
import android.content.Context
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.CameraActivity
import com.openclassrooms.realestatemanager.MainActivity.Companion.CREATEORMODIFY
import com.openclassrooms.realestatemanager.MainActivity.Companion.ID
import com.openclassrooms.realestatemanager.MainActivity.Companion.IMAGES
import com.openclassrooms.realestatemanager.MainActivity.Companion.PHOTO
import com.openclassrooms.realestatemanager.MainActivity.Companion.VIDEOS
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.event.*
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Video_property
import com.openclassrooms.realestatemanager.utils.Utils.getTodayDate2
import com.openclassrooms.realestatemanager.utils.toFrenchDateFormat
import com.openclassrooms.realestatemanager.utils.toNewDateFormat
import kotlinx.android.synthetic.main.fragment_add_dialog.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressWarnings("unchecked")
class MyAddFragment : DialogFragment() {

    val photoList  = ArrayList<Image_property>()
    val photoListStart  = ArrayList<Image_property>()
    lateinit var nb_photo : TextView
    var modify : Property? = null
    var videoList  : ArrayList<Video_property>? = null
    var imageList  : ArrayList<Image_property>? = null
    var nb_alea = 0
    var nb_video = 0
    var mContext: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }


    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_dialog, container, false)
        dialog!!.setTitle(getString(R.string.add_title))


        if (getArguments()!=null) {
            modify = getArguments()!!.getParcelable(CREATEORMODIFY);
            videoList = getArguments()!!.getSerializable(VIDEOS) as ArrayList<Video_property>?
            imageList  = getArguments()!!.getSerializable(IMAGES) as ArrayList<Image_property>?
        }



        val add = rootView.findViewById<Button>(R.id.add)
        val photo = rootView.findViewById<Button>(R.id.photo)
        val ville = rootView.findViewById<TextView>(R.id.ville)
        val prix = rootView.findViewById<TextView>(R.id.price)
        val nb_bedroom = rootView.findViewById<TextView>(R.id.nb_bedroom)
        val nb_bathroom = rootView.findViewById<TextView>(R.id.nb_bathroom)
        val surface = rootView.findViewById<TextView>(R.id.surface)
        val nb_piece = rootView.findViewById<TextView>(R.id.nb_piece)
        val adresse = rootView.findViewById<TextView>(R.id.adresse)
        val agent = rootView.findViewById<TextView>(R.id.agent)
        val proximity = rootView.findViewById<TextView>(R.id.proximity)
        val youtube = rootView.findViewById<TextView>(R.id.youtube)
        val description = rootView.findViewById<TextView>(R.id.description)
        nb_photo = rootView.findViewById<TextView>(R.id.nb_photo)

        val date = rootView.findViewById<TextView>(R.id.selling_date);


        val dialogSpinnerMoney = rootView.findViewById<Spinner>(R.id.spinner_money)
        val dialogSpinnerStatu = rootView.findViewById<Spinner>(R.id.spinner_statut)
        val dialogSpinnerType = rootView.findViewById<Spinner>(R.id.spinner_type)

        nb_alea = (Math.random() * 100000).toInt()

        var startDate = getTodayDate2

        if(modify!=null){
            startDate = modify!!.start_date
            ville.text = modify!!.ville
            prix.text = modify!!.price.toString()
            nb_bedroom.text = modify!!.nb_bedroom.toString()
            nb_bathroom.text = modify!!.nb_bathroom.toString()
            surface.text = modify!!.surface.toString()
            nb_piece.text = modify!!.nb_piece.toString()
            adresse.text = modify!!.address
            agent.text = modify!!.estate_agent
            proximity.text = modify!!.proximity
            description.text = modify!!.description


           if  (modify!!.selling_date!!.compareTo(getString(R.string.date_default2)
                           .toNewDateFormat()) != 0) {
               date.text = modify!!.selling_date!!.toFrenchDateFormat()
           }

            if(modify!!.priceIsDollar.compareTo(getString(R.string.dollar))==0){
                dialogSpinnerMoney.setSelection(1)
            }

            if(modify!!.status.compareTo(getString(R.string.rent))==0){
                dialogSpinnerStatu.setSelection(1)
            }else if(modify!!.status.compareTo(getString(R.string.sold))==0){
                dialogSpinnerStatu.setSelection(2)
            }

            if(modify!!.type.compareTo(getString(R.string.apartment))==0){
                dialogSpinnerType.setSelection(1)
            }else if(modify!!.status.compareTo(getString(R.string.building))==0){
                dialogSpinnerType.setSelection(2)
            }else if(modify!!.status.compareTo(getString(R.string.studio))==0){
                dialogSpinnerType.setSelection(3)
            }else if(modify!!.status.compareTo(getString(R.string.villa))==0){
                dialogSpinnerType.setSelection(4)
            }else if(modify!!.status.compareTo(getString(R.string.other))==0){
                dialogSpinnerType.setSelection(5)
            }

            if ( imageList != null) {
                for (image in imageList!!) {
                    if (modify!!.id == image.id_property) {
                        photoList.add(image)
                        photoListStart.add(image)
                    }
                }
                nb_photo.text = photoList.size.toString()
            }
            photo.setText(getString(R.string.delete_image))

            var youtubeString = ""

            for (video in videoList!!) {
                if (modify!!.id == video.id_property) {
                    youtubeString += video.video + ","
                    nb_video += 1
                }
            }
            if(youtubeString.length>2) {
            youtube.text = youtubeString.substring(0, youtubeString.length - 1)
            }

            nb_alea = modify!!.id

            add.setText(getString(R.string.modify))

        }


        date.setOnClickListener(View.OnClickListener {
            // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR) // current year
            val mMonth = c.get(Calendar.MONTH) // current month
            val mDay = c.get(Calendar.DAY_OF_MONTH) // current day
            // date picker dialog
            val  datePickerDialog = DatePickerDialog(mContext!!,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        var day = dayOfMonth.toString()
                        if(day.length!=2){ day = "0$day" }
                        var month = (monthOfYear + 1).toString()
                        if(month.length!=2){ month = "0" + ( monthOfYear+1)}
                        var texte = "$day/$month/$year"
                        date.text = texte
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        var dateSelling =  getString(R.string.date_default2)

        if (date.text.toString().length>9){
            dateSelling = date.text.toString()
        }

        add.setOnClickListener {

            val dollarEuro = dialogSpinnerMoney.getSelectedItem() as String
            val statut = dialogSpinnerStatu.getSelectedItem() as String
            val type = dialogSpinnerType.getSelectedItem() as String

            val comparison = statut.compareTo(getString(R.string.sold))

           if (prix.text.toString().trim({ it <= ' ' }).isEmpty()) run { prix.setError(getString(R.string.complete))}
            else if (photoList.size<1) run { photo.setError(getString(R.string.error_photo))}
            else if (nb_bedroom.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_bedroom.setError(getString(R.string.complete))}
            else if (nb_bathroom.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_bathroom.setError(getString(R.string.complete))}
            else if (surface.text.toString().trim({ it <= ' ' }).isEmpty()) run { surface.setError(getString(R.string.complete))}
            else if (nb_piece.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_piece.setError(getString(R.string.complete))}
            else if (ville.text.toString().trim({ it <= ' ' }).isEmpty()) run { ville.setError(getString(R.string.complete))}
            else if (adresse.text.toString().trim({ it <= ' ' }).isEmpty()) run { adresse.setError(getString(R.string.complete))}
            else if (agent.text.toString().trim({ it <= ' ' }).isEmpty()) run { agent.setError(getString(R.string.complete))}
            else if (proximity.text.toString().trim({ it <= ' ' }).isEmpty()) run { proximity.setError(getString(R.string.complete))}
            else if (description.text.toString().trim({ it <= ' ' }).isEmpty()) run {description.setError(getString(R.string.complete))}
            else if (comparison == 0 && selling_date.text.toString().trim({ it <= ' ' }).isEmpty()) {

                    run { selling_date.setError(getString(R.string.error_date)) }
            }else if (comparison != 0 && !selling_date.text.toString().trim({ it <= ' ' }).isEmpty()){
                    run { selling_date.setError(getString(R.string.error_date2))}
            }else{

                val youtubeVideos = Vector<String>()

                if (youtube.text.toString().length > 2) {
                    val strings = youtube.text.toString().split(",")
                    for (i in strings.indices) {
                       youtubeVideos.add((strings[i]))
                    }
                }

               var proximityString = proximity.text.toString()
               val proximityStringList = ArrayList<String>()
               if (proximity.text.toString().length > 2) {
                   val strings = proximity.text.toString().split(",")
                   for (i in strings.indices) {
                       proximityStringList.add((strings[i]))
                   }
                   proximityStringList.sort()
                   proximityString = ""
                   for (i in proximityStringList) {
                       proximityString += i + ","                   }

               }

                val property = Property(nb_alea, type, prix.text.toString().toInt(), nb_bedroom.text.toString().toInt(), nb_bathroom.text.toString().toInt(), surface.text.toString().toInt(), nb_piece.text.toString().toInt(), description.text.toString(), ville.text.toString(), adresse.text.toString(), proximityString, statut, startDate, dateSelling.toNewDateFormat(), agent.text.toString(), dollarEuro, photoList.size, youtubeVideos.size)
                sendEvent(property)

                if (modify != null) {
                    for (vidDelete in videoList!!) {
                        if (vidDelete.id_property == modify!!.id) {
                            EventBus.getDefault().post(DeleteVideoEvent(vidDelete))
                        }
                    }
                }

                for (vid in youtubeVideos) {
                    EventBus.getDefault().post(AddVideoEvent(Video_property(0,nb_alea,vid)))
                }

               if(modify != null){
                   if(photo.text.toString().compareTo(getString(R.string.delete_image))!=0){
                        for (photog in photoListStart) {
                          EventBus.getDefault().post(DeleteImageEvent(photog.id))
                       }
                       for (photog in photoList) {
                           EventBus.getDefault().post(AddImageEvent(photog))
                       }
                   }
               }else{
                   for (photog in photoList) {
                       EventBus.getDefault().post(AddImageEvent(photog))
                   }
               }
                dismiss()

            }
        }

        photo.setOnClickListener {
            if(photo.text.toString().compareTo(getString(R.string.delete_image))==0){
                photo.text = getString(R.string.add_photo)

                photoList.clear()
                nb_photo.text = getString(R.string.nb_photo) + photoList.size.toString();

            }else {
                val intent = Intent(mContext, CameraActivity::class.java)
                intent.putExtra(ID, nb_alea )
                startActivityForResult(intent,0)
            }

        }



        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null) {

            if (resultCode == 1) {
                val s: ArrayList<Image_property> = data.getParcelableArrayListExtra(PHOTO)


                for (photo in s) {
                    photoList.add(photo)
                }

                nb_photo.text = getString(R.string.nb_photo) + photoList.size.toString();

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

     fun sendEvent ( property : Property) {

         if(modify==null) {
             Toast.makeText(mContext,getString(R.string.success_add), Toast.LENGTH_SHORT).show()
             EventBus.getDefault().post(AddEvent(property))
         }else{
             Toast.makeText(mContext, getString(R.string.asuccess_modify) , Toast.LENGTH_SHORT).show()
             EventBus.getDefault().post(ModifyEvent(property))
         }
     }

}


