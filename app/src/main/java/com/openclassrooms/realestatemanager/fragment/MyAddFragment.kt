package com.openclassrooms.realestatemanager.fragment

import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.utils.Utils.getTodayDate
import org.greenrobot.eventbus.EventBus

import java.util.*
import android.app.DatePickerDialog
import android.provider.MediaStore
import android.widget.*
import com.openclassrooms.realestatemanager.CameraActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.event.*
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.Video_property
import com.openclassrooms.realestatemanager.utils.Utils.getTodayDate2
import kotlinx.android.synthetic.main.fragment_sample_dialog.*


class MyAddFragment : DialogFragment() {

    val photoList  = ArrayList<Image_property>()
    val photoListStart  = ArrayList<Image_property>()
    lateinit var nb_photo : TextView
    var modify : Property? = null
    var videoList  : ArrayList<Video_property>? = null
    var imageList  : ArrayList<Image_property>? = null
    var nb_alea = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false)
        dialog.setTitle("Ajouter un bien !")


        if (getArguments()!=null) {
            modify = getArguments().getParcelable<Property>("CreateOrModify");
            videoList = getArguments().getSerializable("Videos") as ArrayList<Video_property>?
            imageList  = getArguments().getSerializable("Images") as ArrayList<Image_property>?
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

        if(modify!=null){
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


         /*  if  (modify!!.selling_date!!.compareTo("0000-01-02") != 0) {
               date.text = modify!!.selling_date
           }*/

            if(modify!!.priceIsDollar.compareTo("Dollar")==0){
                dialogSpinnerMoney.setSelection(1)
            }

            if(modify!!.status.compareTo("à louer")==0){
                dialogSpinnerStatu.setSelection(1)
            }else if(modify!!.status.compareTo("vendu")==0){
                dialogSpinnerStatu.setSelection(2)
            }

            if(modify!!.type.compareTo("Appartement")==0){
                dialogSpinnerType.setSelection(1)
            }else if(modify!!.status.compareTo("Immeuble")==0){
                dialogSpinnerType.setSelection(2)
            }else if(modify!!.status.compareTo("Studio")==0){
                dialogSpinnerType.setSelection(3)
            }else if(modify!!.status.compareTo("Villa")==0){
                dialogSpinnerType.setSelection(4)
            }else if(modify!!.status.compareTo("Autre")==0){
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



                // for (photo in modify!!.photo){ photoList.add(photo) }
            photo.setText("Supprimer les photos")

            if ( videoList!! != null) {

                var youtubeString = ""

                for (video in videoList!!) {
                    if (modify!!.id == video.id_property) {
                        youtubeString += video.video + ","
                    }
                }

                if(youtubeString.length>2) {
                youtube.text = youtubeString.substring(0, youtubeString.length - 1)
                }


            }

            nb_alea = modify!!.id

            add.setText("Modifier le bien")

        }




        date.setOnClickListener(View.OnClickListener {
            // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR) // current year
            val mMonth = c.get(Calendar.MONTH) // current month
            val mDay = c.get(Calendar.DAY_OF_MONTH) // current day
            // date picker dialog
            val  datePickerDialog = DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth.toString() + "/"
                                + (monthOfYear + 1) + "/" + year)
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        var dateSelling =  "0000-01-02"

        if (date.text.toString().length>9){
            dateSelling = date.text.toString()
        }







        add.setOnClickListener {



            val dollarEuro = dialogSpinnerMoney.getSelectedItem() as String
            val statut = dialogSpinnerStatu.getSelectedItem() as String
            val type = dialogSpinnerType.getSelectedItem() as String

            val comparison = statut.compareTo("vendu")



           if (prix.text.toString().trim({ it <= ' ' }).isEmpty()) run { prix.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (photoList.size<1) run { photo.setError("Veuillez ajouter au moins une photo")}
            else if (nb_bedroom.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_bedroom.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (nb_bathroom.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_bathroom.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (surface.text.toString().trim({ it <= ' ' }).isEmpty()) run { surface.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (nb_piece.text.toString().trim({ it <= ' ' }).isEmpty()) run { nb_piece.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (ville.text.toString().trim({ it <= ' ' }).isEmpty()) run { ville.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (adresse.text.toString().trim({ it <= ' ' }).isEmpty()) run { adresse.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (agent.text.toString().trim({ it <= ' ' }).isEmpty()) run { agent.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (proximity.text.toString().trim({ it <= ' ' }).isEmpty()) run { proximity.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (description.text.toString().trim({ it <= ' ' }).isEmpty()) run {description.setError("Veuillez renseignez correctement tout les champs obligatoire")}
            else if (comparison == 0 && selling_date.text.toString().trim({ it <= ' ' }).isEmpty()) {

                    run { selling_date.setError("Veuillez renseignez la date de vente quand le bien est vendu") }
            }else if (comparison != 0 && !selling_date.text.toString().trim({ it <= ' ' }).isEmpty()){
                    run { selling_date.setError("Il y a une date alors le bien n'est pas en vente")}
            }else{



                    //val couleurs2 = Arrays.asList(Image_property("https://q-ec.bstatic.com/images/hotel/max1024x768/480/48069729.jpg", "descrip"))

                val youtubeVideos = Vector<String>()

                if (youtube.text.toString().length > 2) {
                    val strings = youtube.text.toString().split(",")
                    for (i in strings.indices) {
                       youtubeVideos.add((strings[i]))
                    }

                }






                val property = Property(nb_alea, type, prix.text.toString().toInt(), nb_bedroom.text.toString().toInt(), nb_bathroom.text.toString().toInt(), surface.text.toString().toInt(), nb_piece.text.toString().toInt(), description.text.toString(), ville.text.toString(), adresse.text.toString(), proximity.text.toString(), statut, getTodayDate2, dateSelling, agent.text.toString(), dollarEuro)
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
                   if(photo.text.toString().compareTo("Supprimer les photos")!=0){
                        for (photo in photoListStart!!) {
                          EventBus.getDefault().post(DeleteImageEvent(photo.id))
                       }
                       for (photo in photoList!!) {
                           EventBus.getDefault().post(AddImageEvent(photo))
                       }
                   }
               }else{
                   for (photo in photoList!!) {
                       EventBus.getDefault().post(AddImageEvent(photo))
                   }
               }






                dismiss()




            }
        }


        photo.setOnClickListener {


            if(photo.text.toString().compareTo("Supprimer les photos")==0){
                photo.text = "Ajouter une photo"

               // for (photo in photoList!!) {
                 //   EventBus.getDefault().post(DeleteImageEvent(photo.id))
                //}

                photoList.clear()
                nb_photo.text = "Nombre de photo ajouté : " + photoList.size.toString();



            }else {


                val intent = Intent(context, CameraActivity::class.java)
                //intent.putExtra("listPhoto", ArrayList<Image_property>())
                intent.putExtra("id", nb_alea )
                startActivityForResult(intent,0)
            }



        }




        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null) {

            if (resultCode == 1) {
                val s: ArrayList<Image_property> = data.getParcelableArrayListExtra("photo")


                for (photo in s) {
                    photoList.add(photo)
                }

                nb_photo.text = "Nombre de photo ajouté : " + photoList.size.toString();

                if (photoList.size > 0) {
                    add.setText("Ajouter le bien")
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


     fun sendEvent ( property : Property) {

         if(modify==null) {
             Toast.makeText(context, "Le bien à bien été ajouté", Toast.LENGTH_SHORT).show()
             EventBus.getDefault().post(AddEvent(property))
         }else{
             Toast.makeText(context, "Le bien à bien été modifié", Toast.LENGTH_SHORT).show()
             EventBus.getDefault().post(ModifyEvent(property))
         }


     }



}


