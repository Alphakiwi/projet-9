package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.MainActivity.Companion.ID
import com.openclassrooms.realestatemanager.database.todolist.PropertyViewModel
import com.openclassrooms.realestatemanager.model.Image_property
import java.io.File
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class CameraActivity : AppCompatActivity() {

    private var takePictureButton: Button? = null
    private var chooseButton: Button? = null
    private var imageView: ImageView? = null
    private var file: Uri? = null
    internal lateinit var photoList: ArrayList<Image_property>
    internal lateinit var descrPhoto: TextView
    internal var id_property: Int = 0

    private val propertyViewModel: PropertyViewModel? = null


    private val outputMediaFile: File?
        get() {
            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "CameraDemo")

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return File(mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())


        val i = intent


        photoList = ArrayList()
        id_property = i.getIntExtra(ID, 0)

        takePictureButton = findViewById<View>(R.id.button_image) as Button
        chooseButton = findViewById<View>(R.id.button_choose) as Button
        descrPhoto = findViewById(R.id.description_photo)


        imageView = findViewById<View>(R.id.imageview) as ImageView


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton!!.isEnabled = false
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton!!.isEnabled = true
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {

                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.file)
                        .into(this.imageView!!)


            }
        }

        if (requestCode == 200) {

            if (resultCode == Activity.RESULT_OK) {


                this.file = data!!.data
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.file)
                        .into(this.imageView!!)

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
            }
        }


    }


    fun takePicture(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = Uri.fromFile(outputMediaFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
        startActivityForResult(intent, 100)
    }

    fun choosePicture(view: View) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 200)
    }

    fun finishThis(view: View) {

        val i2 = Intent()

        if (!descrPhoto.text.toString().trim { it <= ' ' }.isEmpty()) {


            photoList.add(Image_property(0, id_property, file!!.toString(), descrPhoto.text.toString()))

            i2.putExtra("photo", photoList)
            this.setResult(1, i2)
            this.finish()
        } else {
            descrPhoto.error = "Ajouter une description à la photo !"
        }


    }

    fun cancelThis(view: View) {

        val i2 = Intent()


        i2.putExtra("listPhoto", ArrayList<Image_property>())
        this.setResult(1, i2)
        this.finish()
    }


    private fun imageView2Bitmap(view: ImageView): Bitmap {
        return (view.drawable as BitmapDrawable).bitmap
    }

}
