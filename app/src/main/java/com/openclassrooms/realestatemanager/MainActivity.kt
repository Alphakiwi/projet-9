package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.openclassrooms.realestatemanager.Utils.haveInternetConnection
import java.util.*


class MainActivity : AppCompatActivity() {

    private var textViewMain: TextView? = null
    private var textViewQuantity: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main)
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity)

        this.configureTextViewMain()
        this.configureTextViewQuantity()
    }

    private fun configureTextViewMain() {
        this.textViewMain!!.textSize = 15f
        val couleurs = Arrays.asList(Image_property("http.test", "descrip"))
        val property = Property("Maison", 120000, 135, 4, "belle maison", couleurs, null, "49 rue de la paix", Arrays.asList("école", "métro"), true, Date(1999, 5, 26), null, "Denis")
        this.textViewMain!!.text = "Le premier bien immobilier enregistré vaut $property"
    }

    private fun configureTextViewQuantity() {
        val quantity = Utils.convertDollarToEuro(100)
        this.textViewQuantity!!.textSize = 20f
        this.textViewQuantity!!.text = quantity.toString() + haveInternetConnection(this)
    }
}