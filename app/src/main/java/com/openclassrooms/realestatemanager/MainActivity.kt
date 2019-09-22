package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.Utils.convertDollarToEuro
import com.openclassrooms.realestatemanager.Utils.convertEuroToDollar
import com.openclassrooms.realestatemanager.Utils.getTodayDate
import extensions.toVideoUrl

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


class MainActivity : AppCompatActivity() {

    internal var listFragment = ListFragment()
    val properties = ArrayList<Property>()
    private lateinit var lastProperty : Property
    val fragmentManager = supportFragmentManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        getSupportActionBar()?.setTitle(getTodayDate);



        val youtubeVideos = Vector<YouTubeVideos>()

        youtubeVideos.add(YouTubeVideos("https://www.youtube.com/watch?v=IdvFBL4Kalo".toVideoUrl()))
        youtubeVideos.add(YouTubeVideos("https://www.youtube.com/watch?v=Vg729rnWsm0".toVideoUrl()))


        //youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IdvFBL4Kalo\" frameborder=\"0\" allowfullscreen></iframe>") );
        /* youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/sdUUx5FdySs\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/91SsmqV47yQ\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i_Q22nDXfq8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mtDokmIKRp4\" frameborder=\"0\" allowfullscreen></iframe>") );
*/


        val couleurs = Arrays.asList(Image_property("https://www.maisons-ossature-bois-chalets-charpente-favre-felix.com/images/maison-bois/maison-bois-65.jpg",
                "Vue extérieur"), Image_property("https://listspirit.com/wp-content/uploads/2017/08/deco-salon-amenagement-interieur-moderne-dune-maison-au-canada.jpg", "Vue intérieur"),
                 Image_property(
                         "https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg",
                         "Chambre"
                 ),Image_property(
                "http://www.bainsetsolutions.fr/scripts/files/5603e6f7554961.58606024/perspective-3d-1-renovation-salle-de-bain-st-gilles-bainsetsolutions-pace-rennes.jpg",
                "Salle de bain" ))
        val couleurs2 = Arrays.asList(Image_property("https://q-ec.bstatic.com/images/hotel/max1024x768/480/48069729.jpg", "descrip"))

        val property = Property(1, "Maison", 120000, 3, 1, 135, 4, "belle maison", couleurs, null, "Lille", "49 rue de la paix", "école, métro", "vendu","26/06/1999", null, "Denis","Euro")
        val appart = Property(2, "Appartement", 70000, 3, 1, 135, 4, "belle maison", couleurs2, youtubeVideos, "Villeneuve d'Ascq", "49 rue de la paix", "école, métro", "à vendre", "26/06/1999", null, "Denis","Euro")

        properties.add(property)
        properties.add(appart)
        properties.add(property)
        properties.add(appart)
        properties.add(property)
        properties.add(appart)

        lastProperty = properties.get(0);


        val args = Bundle()
        args.putSerializable("properties", properties)
        listFragment.setArguments(args)

        fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit()

        val button = findViewById(R.id.fab) as FloatingActionButton


        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {


                fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit()


            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater

        //R.menu.menu est l'id de notre menu

        inflater.inflate(R.menu.menu, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val i1 = item.itemId
        when (i1) {
            R.id.mapItem -> {



                return true
            }
            R.id.costItem-> {

                for (property in properties){
                    if (property.priceIsDollar == "Dollar") {
                        property.price = convertEuroToDollar(property.price)
                        property.priceIsDollar =  "Euro"
                        item.setIcon(R.drawable.ic_euro)
                    }else{
                        property.price = convertDollarToEuro(property.price)
                        property.priceIsDollar =  "Dollar"
                        item.setIcon(R.drawable.ic_dollar)
                    }
                }

                var listFragment2 = ListFragment()
                val args = Bundle()
                args.putSerializable("properties", properties)
                listFragment2.setArguments(args)
                fragmentManager.beginTransaction().replace(R.id.content_frame,  listFragment2).commit()

                Toast.makeText(this, "La monnaie à bien été converti", Toast.LENGTH_SHORT).show()




                return true

            }
            R.id.modifyItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyDialogFragment()
                dialogFragment.show(fm, "Sample Fragment")

                val args = Bundle()
                args.putParcelable("CreateOrModify", lastProperty )
                dialogFragment.arguments = args
                return true



                return true

            }
            R.id.addItem -> {

                val fm = getFragmentManager()
                val dialogFragment = MyDialogFragment()
                dialogFragment.show(fm, "Sample Fragment")

                return true


            }

            R.id.researchItem -> {



                return true

            }
        }



        return super.onOptionsItemSelected(item)
    }

    @Subscribe
    fun onActualise(event: ActualiseEvent) {

        val detailFragment = DetailFragment()
        // Supply index input as an argument.
        val args = Bundle()
        args.putParcelable("property", event.property)
        detailFragment.setArguments(args)


        fragmentManager.beginTransaction().replace(R.id.content_frame, detailFragment).commit()

        lastProperty = event.property

    }

    @Subscribe
    fun onAdd(event: AddEvent) {

        properties.add(event.property)
        listFragment.adapter.notifyDataSetChanged()


    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


}



