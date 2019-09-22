package com.openclassrooms.realestatemanager

import android.app.AlertDialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.Utils.getTodayDate
import extensions.toVideoUrl
import org.greenrobot.eventbus.EventBus

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.app.DatePickerDialog
import android.widget.*
import kotlinx.android.synthetic.main.fragment_sample_dialog.*


class MyDialogFragment : DialogFragment() {

    val photoList  = ArrayList<Image_property>()
    lateinit var nb_photo : TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false)
        dialog.setTitle("Ajouter un bien !")

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

        val date = rootView.findViewById<EditText>(R.id.selling_date);

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







        add.setOnClickListener {

            val dialogSpinnerMoney = rootView.findViewById<Spinner>(R.id.spinner_money)
            val dialogSpinnerStatu = rootView.findViewById<Spinner>(R.id.spinner_statut)
            val dialogSpinnerType = rootView.findViewById<Spinner>(R.id.spinner_type)



            val dollarEuro = dialogSpinnerMoney.getSelectedItem() as String
            val statut = dialogSpinnerStatu.getSelectedItem() as String
            val type = dialogSpinnerType.getSelectedItem() as String

            val comparison = statut.compareTo("vendu")



            if (prix.text.toString().trim({ it <= ' ' }).isEmpty()) run { prix.setError("Veuillez renseignez correctement tout les champs obligatoire")}
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



                    Toast.makeText(context, "Le bien qui est " + " valant " + prix.text.toString() + " à " + ville.text.toString() + " à bien été ajouté", Toast.LENGTH_SHORT).show()
                    //val couleurs2 = Arrays.asList(Image_property("https://q-ec.bstatic.com/images/hotel/max1024x768/480/48069729.jpg", "descrip"))

                    val youtubeVideos = Vector<YouTubeVideos>()

                if (youtube.text.toString().length > 0) {
                    val strings = youtube.text.toString().split(",")
                    for (i in strings.indices) {
                       youtubeVideos.add(YouTubeVideos(strings[i].toVideoUrl()))
                    }

                }

                    if (youtube.text.toString().length < 2) {
                        val property = Property(1, type, prix.text.toString().toInt(), nb_bedroom.text.toString().toInt(), nb_bathroom.text.toString().toInt(), surface.text.toString().toInt(), nb_piece.text.toString().toInt(), description.text.toString(), photoList, youtubeVideos, ville.text.toString(), adresse.text.toString(), Arrays.asList<String>(proximity.text.toString(), "métro"), statut, getTodayDate, date.text.toString(), agent.text.toString(), dollarEuro)
                        EventBus.getDefault().post(AddEvent(property))
                    } else {
                        val property = Property(1, type, prix.text.toString().toInt(), nb_bedroom.text.toString().toInt(), nb_bathroom.text.toString().toInt(), surface.text.toString().toInt(), nb_piece.text.toString().toInt(), description.text.toString(), photoList, youtubeVideos, ville.text.toString(), adresse.text.toString(), Arrays.asList<String>(proximity.text.toString(), "métro"), statut, getTodayDate, date.text.toString(), agent.text.toString(), dollarEuro)
                        EventBus.getDefault().post(AddEvent(property))

                    }


                    dismiss()



            }
        }


        photo.setOnClickListener {


            val intent = Intent(context, CameraActivity::class.java)
            intent.putExtra("listPhoto",  ArrayList<Image_property>())

            startActivityForResult(intent,0)


        }








        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (resultCode == 1) {
            val s : ArrayList<Image_property>  = data.getParcelableArrayListExtra("listPhoto")


            for (photo in s){
                photoList.add(photo)
            }

            nb_photo.text =  nb_photo.text.toString()  + " " + photoList.size.toString();
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}


