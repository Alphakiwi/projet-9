package com.openclassrooms.realestatemanager.fragment

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus

import java.util.*
import android.app.DatePickerDialog
import android.content.Context
import android.widget.*
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.event.SearchEvent
import com.openclassrooms.realestatemanager.utils.toNewDateFormat


class MyResearchFragment : DialogFragment() {

    var mContext: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_research_dialog, container, false)
        dialog!!.setTitle(getString(R.string.research))


        var _type = getString(R.string.pourcentage)
        var _priceMin = getString(R.string.minimum)
        var _surfaceMin = getString(R.string.minimum)
        var _pieceMin = getString(R.string.minimum)
        var _priceMax = getString(R.string.maximum)
        var _surfaceMax = getString(R.string.maximum)
        var _pieceMax = getString(R.string.maximum)
        var _descript = getString(R.string.pourcentage)
        var _ville = getString(R.string.pourcentage)
        var _address = getString(R.string.pourcentage)
        var _proximity = getString(R.string.pourcentage)
        var _statu = getString(R.string.pourcentage)
        var _startDate = getString(R.string.date_default)
        var _sellingDate = getString(R.string.date_default)
        var _agent = getString(R.string.pourcentage)
        var _isDollar = getString(R.string.pourcentage)
        var _photoMin = getString(R.string.minimum)
        var _photoMax = getString(R.string.maximum)
        var _videoMin = getString(R.string.minimum)
        var _videoMax = getString(R.string.maximum)

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
            val  datePickerDialog = DatePickerDialog(mContext!!,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        var day = dayOfMonth.toString()
                        if(day.length!=2){ day = "0$day" }
                        var month = (monthOfYear + 1).toString()
                        if(month.length!=2){ month = "0" + ( monthOfYear+1)}
                        var texte = "$day/$month/$year"
                        startDate.text = texte
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
            val  datePickerDialog = DatePickerDialog(mContext!!,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        var day = dayOfMonth.toString()
                        if(day.length!=2){ day = "0$day"
                        }
                        var month = (monthOfYear + 1).toString()
                        if(month.length!=2){ month = "0" + ( monthOfYear+1)}
                        var texte = "$day/$month/$year"
                        sellingDate.text = texte
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        research.setOnClickListener {

            var canSearch = true

                if (switchStatut.isChecked()) { _statu = spinnerStatut.getSelectedItem() as String }

                if (switchType.isChecked()) { _type = spinnerType.getSelectedItem() as String }

                if (switchMoney.isChecked()) { _isDollar = spinnerMoney.getSelectedItem() as String }

                if (switchPrice.isChecked()) {

                    _priceMin = prixMin.text.toString()
                    _priceMax = prixMax.text.toString()

                    if (prixMin.text.toString().length<1) run { prixMin
                            .setError(getString(R.string.complete)); canSearch = false}
                    if (prixMax.text.toString().length<1) run { prixMax
                            .setError(getString(R.string.complete)); canSearch = false}

                }



                if (switchSurface.isChecked()) {
                    _surfaceMin = surfaceMin.text.toString()
                    _surfaceMax = surfaceMax.text.toString()

                    if (surfaceMin.text.toString().length<1) run { surfaceMin.setError(getString(R.string.complete)); canSearch = false}
                    if (surfaceMax.text.toString().length<1) run { surfaceMax.setError(getString(R.string.complete)); canSearch = false}
                }

                if (switchNbPiece.isChecked()) {
                    _pieceMin = nbPieceMin.text.toString()
                    _pieceMax = nbPieceMax.text.toString()

                    if (nbPieceMin.text.toString().length<1) run { nbPieceMin.setError(getString(R.string.complete)); canSearch = false}
                    if (nbPieceMax.text.toString().length<1) run { nbPieceMax.setError(getString(R.string.complete)); canSearch = false}
                }

                if (switchVille.isChecked()) {
                    _ville =  "%" + ville.text.toString() + "%"

                    if (ville.text.toString().length<1) run { ville.setError(getString(R.string.complete)); canSearch = false}

                }

                if (switchAdress.isChecked()) {
                    _address = "%" + address.text.toString() + "%"
                    if (address.text.toString().length<1) run { address.setError(getString(R.string.complete)); canSearch = false}
                }

                if (switchAgent.isChecked()) {
                    _agent = "%" + agent.text.toString() + "%"
                    if (agent.text.toString().length<1) run { agent.setError(getString(R.string.complete)); canSearch = false}
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

                    if (proximity.text.toString().length<1) run { proximity.setError(getString(R.string.complete)); canSearch = false}


                }




                if (switchYoutube.isChecked()) {
                    _videoMin = nbVideoMin.text.toString()
                    _videoMax = nbVideoMax.text.toString()

                    if (nbVideoMin.text.toString().length<1) run { nbVideoMin.setError(getString(R.string.complete)); canSearch = false}
                    if (nbVideoMax.text.toString().length<1) run { nbVideoMax.setError(getString(R.string.complete)); canSearch = false}


                }

                if (switchDescription.isChecked()) {

                    _descript = "%" + description.text.toString() +  "%"

                    if (description.text.toString().length<1) run { description.setError(getString(R.string.complete)); canSearch = false}

                }

                if (switchPhoto.isChecked()) {
                    _photoMin = nbPhotoMin.text.toString()
                    _photoMax = nbPhotoMax.text.toString()

                    if (nbPhotoMin.text.toString().length<1) run { nbPhotoMin.setError(getString(R.string.complete)); canSearch = false}
                    if (nbPhotoMax.text.toString().length<1) run { nbPhotoMax.setError(getString(R.string.complete)); canSearch = false}

                }

                if (switchStart.isChecked()) {

                    _startDate = startDate.text.toString()
                    if (startDate.text.toString().length!=10) run {startDate.setError(getString(R.string.complete)); canSearch = false}
                }

                if (switchSelling.isChecked()) {

                    _sellingDate = sellingDate.text.toString()
                    if (sellingDate.text.toString().length!=10) run {sellingDate.setError(getString(R.string.complete)); canSearch = false}

                }


                if(canSearch == true) {
                    EventBus.getDefault().post(SearchEvent(_type,  _priceMin.toInt(),  _surfaceMin.toInt(),
                            _pieceMin.toInt(),  _priceMax.toInt() ,  _surfaceMax.toInt(),  _pieceMax.toInt(),
                            _descript,  _ville,  _address,  _proximity,  _statu,  _startDate.toNewDateFormat(),
                            _sellingDate.toNewDateFormat(),  _agent,  _isDollar, _photoMin.toInt(),
                            _photoMax.toInt(), _videoMin.toInt(), _videoMax.toInt()))
                    dismiss()
                }


        }





        return rootView
    }


}


