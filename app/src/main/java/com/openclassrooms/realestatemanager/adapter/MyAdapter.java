package com.openclassrooms.realestatemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.event.DetailEvent;
import com.openclassrooms.realestatemanager.model.Image_property;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Property> properties ;
    ArrayList<Image_property> images ;
    LinearLayout previousSelected = null;




    public MyAdapter(Context c, ArrayList<Property> p, ArrayList<Image_property> i){
        context = c;
        properties = p;
        images = i;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    public void updateData(ArrayList<Property> properties){
        this.properties = properties;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(properties.get(position).getType());
        holder.descript.setText(properties.get(position).getVille());


        if (properties.get(position).getPriceIsDollar().compareTo(context.getString(R.string.dollar)) == 0) {
            holder.price.setText("$ " + properties.get(position).getPrice());
        }else{
            holder.price.setText(properties.get(position).getPrice() + " €");
        }

        ArrayList<String> listImages = new ArrayList<String>();

        if (images != null) {
            for ( Image_property image : images ) {
                if (properties.get(position).getId() ==  image.getId_property() ) {
                    listImages.add(image.getImage());
                }
            }
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {



                if (previousSelected!=null){
                    previousSelected.setBackgroundColor(WHITE);
                }



                holder.root.setBackgroundColor(R.color.colorAccent);

                previousSelected = holder.root;



                Glide.with(holder.avatar.getContext())
                        .load(listImages.get(0))
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.avatar);


                EventBus.getDefault().post(new DetailEvent(properties.get(position)));

            }
        });

        if (listImages.size()>0) {
            Glide.with(holder.avatar.getContext())
                    .load(listImages.get(0))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.avatar);
        }

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, descript, price ;
        LinearLayout root;
        ImageView avatar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            descript =  itemView.findViewById(R.id.list_desc);
            avatar = itemView.findViewById(R.id.list_image);
            price = itemView.findViewById(R.id.list_price);
            root = itemView.findViewById(R.id.list_root);

        }
    }
}
