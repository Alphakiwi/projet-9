package com.openclassrooms.realestatemanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.MainActivity.Companion.IMAGES
import com.openclassrooms.realestatemanager.MainActivity.Companion.PROPERTIES
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapter.MyAdapter
import com.openclassrooms.realestatemanager.model.Image_property
import com.openclassrooms.realestatemanager.model.Property


import java.util.ArrayList


class ListFragment : Fragment() {

    internal lateinit var myView: View
    internal lateinit var adapter: MyAdapter
    private var mContext: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.user_recycler, container, false)


        val args = arguments
        val properties = args!!.getSerializable(PROPERTIES) as ArrayList<Property>?
        val images = args.getSerializable(IMAGES) as ArrayList<Image_property>?



        val recyclerView = myView.findViewById<View>(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MyAdapter(context, properties, images)
        recyclerView.adapter = adapter



        return myView

    }

}
