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
        val rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false)
        dialog.setTitle("Rechercher des biens !")







        return rootView
    }


}


