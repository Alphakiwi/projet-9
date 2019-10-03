package com.openclassrooms.realestatemanager.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.R;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
   // private List<Image_property> imageIDs;
    private List<byte[]> imageIDs;
    private int itemBackground;
    public ImageAdapter(Context c, List<byte[]> IDs)
    {
        context = c;
        imageIDs = IDs ;
        TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();
    }
    // returns the number of images
    public int getCount() {
        return imageIDs.size();
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }
    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);



        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageIDs.get(position));
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);

        Glide.with(context)
                .load(theImage)
                .into(imageView);

        //Glide.with(context)
               // .load("https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg")
               // .into(imageView);

        //imageView.setImageBitmap( BitmapFactory.decodeByteArray(imageIDs.get(position).getImage(), 0, imageIDs.get(position).getImage().length));



        imageView.setLayoutParams(new Gallery.LayoutParams(250, 250));
        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}

