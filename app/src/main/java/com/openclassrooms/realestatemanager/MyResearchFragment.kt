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


class MyResearchFragment : DialogFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_research_dialog, container, false)
        dialog.setTitle("Rechercher des biens !")


        val args = arguments
        val properties = args?.getSerializable("properties") as ArrayList<Property>

        properties.add(properties.get(0))



        val research = rootView.findViewById<Button>(R.id.search)

        val spinnerStatut = rootView.findViewById<Spinner>(R.id.spinner_statut)
        val switchStatut = rootView.findViewById<Switch>(R.id.switch_statu)

        val spinnerType = rootView.findViewById<Spinner>(R.id.spinner_type)
        val switchType = rootView.findViewById<Switch>(R.id.switch_type)

        val spinnerMoney = rootView.findViewById<Spinner>(R.id.spinner_money)
        val switchMoney = rootView.findViewById<Switch>(R.id.switch_money)

        val prixMin = rootView.findViewById<TextView>(R.id.price_min)
        val prixMax = rootView.findViewById<TextView>(R.id.price_max)
        val switchPrice = rootView.findViewById<Switch>(R.id.switch_price)

        val bedMin = rootView.findViewById<TextView>(R.id.nb_bedroom_min)
        val bedMax = rootView.findViewById<TextView>(R.id.nb_bedroom_max)
        val switchBed = rootView.findViewById<Switch>(R.id.switch_bed)

        val bathMin = rootView.findViewById<TextView>(R.id.nb_bathroom_min)
        val bathMax = rootView.findViewById<TextView>(R.id.nb_bathroom_max)
        val switchBath = rootView.findViewById<Switch>(R.id.switch_bath)

        val surfaceMin = rootView.findViewById<TextView>(R.id.surface_min)
        val surfaceMax = rootView.findViewById<TextView>(R.id.surface_max)
        val switchSurface = rootView.findViewById<Switch>(R.id.switch_surface)

        val nbPieceMin = rootView.findViewById<TextView>(R.id.nb_piece_min)
        val nbPieceMax = rootView.findViewById<TextView>(R.id.nb_piece_max)
        val switchNbPiece = rootView.findViewById<Switch>(R.id.switch_piece)

        val ville = rootView.findViewById<TextView>(R.id.ville)
        val switchVille = rootView.findViewById<Switch>(R.id.switch_ville)

        val address = rootView.findViewById<TextView>(R.id.adresse)
        val switchAdress = rootView.findViewById<Switch>(R.id.switch_adresse)

        val agent = rootView.findViewById<TextView>(R.id.agent)
        val switchAgent = rootView.findViewById<Switch>(R.id.switch_agent)

        val proximity = rootView.findViewById<TextView>(R.id.proximity)
        val switchProximity = rootView.findViewById<Switch>(R.id.switch_proximity)

        val nbVideoMin = rootView.findViewById<TextView>(R.id.nb_video_min)
        val nbVideoMax = rootView.findViewById<TextView>(R.id.nb_video_max)
        val switchYoutube = rootView.findViewById<Switch>(R.id.switch_youtube)

        val description = rootView.findViewById<TextView>(R.id.description)
        val switchDescription = rootView.findViewById<Switch>(R.id.switch_description)

        val nbPhotoMin = rootView.findViewById<TextView>(R.id.nb_photo_min)
        val nbPhotoMax = rootView.findViewById<TextView>(R.id.nb_photo_max)
        val switchPhoto = rootView.findViewById<Switch>(R.id.switch_photo)

        val startDate = rootView.findViewById<TextView>(R.id.start_date)
        val switchStart = rootView.findViewById<Switch>(R.id.switch_start)


        val sellingDate = rootView.findViewById<TextView>(R.id.selling_date)
        val switchSelling = rootView.findViewById<Switch>(R.id.switch_selling)

        startDate.setOnClickListener(View.OnClickListener {
            // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR) // current year
            val mMonth = c.get(Calendar.MONTH) // current month
            val mDay = c.get(Calendar.DAY_OF_MONTH) // current day
            // date picker dialog
            val  datePickerDialog = DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        startDate.setText(dayOfMonth.toString() + "/"
                                + (monthOfYear + 1) + "/" + year)
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        sellingDate.setOnClickListener(View.OnClickListener {
            // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR) // current year
            val mMonth = c.get(Calendar.MONTH) // current month
            val mDay = c.get(Calendar.DAY_OF_MONTH) // current day
            // date picker dialog
            val  datePickerDialog = DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        sellingDate.setText(dayOfMonth.toString() + "/"
                                + (monthOfYear + 1) + "/" + year)
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        research.setOnClickListener {

            EventBus.getDefault().post(SearchEvent(properties))
            dismiss()

        }





        return rootView
    }


}


