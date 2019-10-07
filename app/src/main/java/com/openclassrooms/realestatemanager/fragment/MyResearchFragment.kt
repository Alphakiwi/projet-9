package com.openclassrooms.realestatemanager.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus

import java.util.*
import android.app.DatePickerDialog
import android.widget.*
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTIES
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.event.SearchEvent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.toNewDateFormat
import kotlinx.android.synthetic.main.fragment_research_dialog.*


class MyResearchFragment : DialogFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_research_dialog, container, false)
        dialog.setTitle("Rechercher des biens !")

        var _type ="%%"
        var _priceMin = "0"
        var _surfaceMin = "0"
        var _pieceMin = "0"
        var _priceMax = "999999999"
        var _surfaceMax = "999999999"
        var _pieceMax = "999999999"
        var _descript ="%%"
        var _ville ="%%"
        var _address ="%%"
        var _proximity ="%%"
        var _statu ="%%"
        var _startDate ="01/01/0000"
        var _sellingDate = "01/01/0000"
        var _agent ="%%"
        var _isDollar ="%%"
        var _photoMin = "0"
        var _photoMax = "999999999"
        var _videoMin = "0"
        var _videoMax = "999999999"


        val args = arguments
        var properties = args?.getSerializable(PROPERTIES) as ArrayList<Property>
        var propertiesCopy  = ArrayList<Property>(properties)


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
                        var day = dayOfMonth.toString()
                        if(day.length!=2){ day = "0" + day}
                        var month = (monthOfYear + 1).toString()
                        if(month.length!=2){ month = "0" + ( monthOfYear+1)}
                        startDate.setText( day + "/"
                                + month  + "/" + year)
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
                        var day = dayOfMonth.toString()
                        if(day.length!=2){ day = "0" + day}
                        var month = (monthOfYear + 1).toString()
                        if(month.length!=2){ month = "0" + ( monthOfYear+1)}
                        sellingDate.setText( day + "/"
                                + month  + "/" + year)
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        research.setOnClickListener {


                if (switchStatut.isChecked()) {

                    _statu = spinnerStatut.getSelectedItem() as String

                }

                if (switchType.isChecked()) {

                    _type = spinnerType.getSelectedItem() as String
                }

                if (switchMoney.isChecked()) {

                    _isDollar = spinnerMoney.getSelectedItem() as String
                }


                if (switchPrice.isChecked()) {

                    _priceMin = prixMin.text.toString()
                    _priceMax = prixMax.text.toString()
                }



                if (switchSurface.isChecked()) {
                    _surfaceMin = surfaceMin.text.toString()
                    _surfaceMax = surfaceMax.text.toString()
                }

                if (switchNbPiece.isChecked()) {
                    _pieceMin = nb_piece_min.text.toString()
                    _pieceMax = nb_piece_max.text.toString()
                }

                if (switchVille.isChecked()) {
                    _ville =  "%" + ville.text.toString() + "%"
                }

                if (switchAdress.isChecked()) {
                    _address = "%" + address.text.toString() + "%"
                }

                if (switchAgent.isChecked()) {
                    _agent = "%" + agent.text.toString() + "%"
                }

                if (switchProximity.isChecked()) {

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
                            proximityString +=   i + "%"
                        }

                    }
                    _proximity = "%" + proximityString + "%"
                    Toast.makeText(context, _proximity, Toast.LENGTH_SHORT).show()

                }




                if (switchYoutube.isChecked()) {
                    _videoMin = nbVideoMin.text.toString()
                    _videoMax = nbVideoMax.text.toString()
                }

                if (switchDescription.isChecked()) {

                    _descript = "%" + description.text.toString() +  "%"
                }

                if (switchPhoto.isChecked()) {
                    _photoMin = nbPhotoMin.text.toString()
                    _photoMax = nbPhotoMax.text.toString()
                                   }

                if (switchStart.isChecked()) {

                    _startDate = startDate.text.toString()
                    "01/01/0000"
                }

                if (switchSelling.isChecked()) {

                    _sellingDate = sellingDate.text.toString()
                }




                EventBus.getDefault().post(SearchEvent(_type,  _priceMin.toInt(),  _surfaceMin.toInt(),  _pieceMin.toInt(),  _priceMax.toInt() ,  _surfaceMax.toInt(),  _pieceMax.toInt(),  _descript,  _ville,  _address,  _proximity,  _statu,  _startDate.toNewDateFormat(),  _sellingDate.toNewDateFormat(),  _agent,  _isDollar, _photoMin.toInt(), _photoMax.toInt(), _videoMin.toInt(), _videoMax.toInt()))
                dismiss()


        }





        return rootView
    }


}


