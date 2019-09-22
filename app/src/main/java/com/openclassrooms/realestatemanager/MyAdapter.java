package com.openclassrooms.realestatemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Property> properties ;
    LinearLayout previousSelected = null;




    public MyAdapter(Context c, ArrayList<Property> p){
        context = c;
        properties = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(properties.get(position).getType());
        holder.descript.setText(properties.get(position).getVille());

        if (properties.get(position).getPriceIsDollar().compareTo("Dollar") == 0) {
            holder.price.setText("$ " + String.valueOf(properties.get(position).getPrice()));
        }else{
            holder.price.setText(String.valueOf(properties.get(position).getPrice()) + " €");
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
                        .load(properties.get(position).getPhoto().get(0).getImage())
                        //.load("https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg")
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.avatar);



                // Intent i = new Intent(context, DetailRestaurantActivity.class);
                //i.putExtra(RESTAURANT, properties.get(position).getResto());

               // context.startActivity(i);

                EventBus.getDefault().post(new ActualiseEvent(properties.get(position)));

            }
        });


        Glide.with(holder.avatar.getContext())
                .load(properties.get(position).getPhoto().get(0).getImage())
                //.load("https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatar);


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
