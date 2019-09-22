package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.*;

public class CameraActivity extends AppCompatActivity {

    private Button takePictureButton;
    private Button chooseButton;
    private ImageView imageView;
    private Uri file;
    private Uri uriImageSelected;
    private Drawable draw;
    ArrayList<Image_property> photoList;
    TextView descrPhoto;
    //Firebase
    Uri downloadUrl;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        Intent i = getIntent();
        photoList = i.getParcelableArrayListExtra("listPhoto");

        takePictureButton = (Button) findViewById(R.id.button_image);
        chooseButton = (Button) findViewById(R.id.button_choose);
        descrPhoto = findViewById(R.id.description_photo);


        imageView = (ImageView) findViewById(R.id.imageview);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                // imageView.setImageURI(file);

                // this.uriImageSelected = data.getData();
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.file)
                        .into(this.imageView);

               // imageView.setRotation(90);

                // uploadImage();
                uploadPhotoInFirebase();


                // draw = imageView.getDrawable();

               /*Bitmap bitmap = (Bitmap)((BitmapDrawable) draw).getBitmap();
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
               byte[] byteArray = stream.toByteArray();*/
                // photoList.add(new Image_property(byteArray," test "));            }
            }
        }

            if (requestCode == 200) {

                if (resultCode == RESULT_OK) {


                    this.file = data.getData();
                    Glide.with(this) //SHOWING PREVIEW OF IMAGE
                            .load(this.file)
                            .into(this.imageView);
                    uploadPhotoInFirebase();
                    /*
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                    imageView.setRotation(0);
                    draw = imageView.getDrawable();
                    Bitmap bitmap = (Bitmap)((BitmapDrawable) draw).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    photoList.add(new Image_property(byteArray," test "));*/

                } else {
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
            }


    }


        public void takePicture (View view){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, 100);
        }

        public void choosePicture (View view){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 200);
        }

        public void finishThis (View view){

            Intent i2 = new Intent();

           // uploadImage();

            if( downloadUrl!= null) {
                if (!descrPhoto.getText().toString().trim().isEmpty()) {
                    photoList.add(new Image_property(downloadUrl.toString(), descrPhoto.getText().toString()));
                    i2.putExtra("listPhoto", photoList);
                    this.setResult(1, i2);
                    this.finish();
                }else{
                    descrPhoto.setError("Ajouter une description à la photo !");
                }
            }else{
                descrPhoto.setError("Ajouter une photo !");

            }



        }

    public void cancelThis (View view){

        Intent i2 = new Intent();


        i2.putExtra("listPhoto",new ArrayList<Image_property>());
        this.setResult(1, i2);
        this.finish();
    }






        private static File getOutputMediaFile () {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "CameraDemo");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            return new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        }

     /*   // 1 - Upload a picture in Firebase and send a message
        private void uploadPhotoInFirebase ( ){
            String uuid = randomUUID().toString(); // GENERATE UNIQUE STRING
            // A - UPLOAD TO GCS
            StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
            mImageRef.putFile(this.uriImageSelected)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String pathImageSavedInFirebase = taskSnapshot.getMetadata().getDownloadUrl().toString();

                            photoList.add(new Image_property(pathImageSavedInFirebase, " test "));
                        }
                    });
        }*/


    private void uploadPhotoInFirebase() {
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        // A - UPLOAD TO GCS
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
        mImageRef.putFile(this.file)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri;

                            }
                        });
                    }
                });
    }

    private void uploadImage() {

        if(file != null)
        {

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CameraActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CameraActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}
