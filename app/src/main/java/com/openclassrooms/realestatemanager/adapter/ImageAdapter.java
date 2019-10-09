package com.openclassrooms.realestatemanager.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
   // private List<Image_property> imageIDs;
    private List<String> imageIDs;
    private int itemBackground;
    public ImageAdapter(Context c, List<String> IDs)
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


        Glide.with(context)
                .load(imageIDs.get(position))
                .into(imageView);


        imageView.setLayoutParams(new Gallery.LayoutParams(250, 250));
        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}

