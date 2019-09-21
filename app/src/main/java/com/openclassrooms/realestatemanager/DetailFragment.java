package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class DetailFragment extends Fragment {

    View myView;
    private Context mContext;

    RecyclerView recyclerView;





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_detail, container, false);


        Bundle args = getArguments();
        Property property = (Property) args.getParcelable("property");

        String soldate = " ";
        if (property.getSelling_date() != null){
            soldate += property.getSelling_date();
        }

        TextView type = myView.findViewById(R.id.type); type.setText(type.getText() + property.getType());
        TextView money = myView.findViewById(R.id.money);
        TextView surface = myView.findViewById(R.id.surface); surface.setText(surface.getText() + " " +property.getSurface());
        TextView home = myView.findViewById(R.id.home);home.setText(home.getText() + " " +  property.getNb_piece());
        TextView bed = myView.findViewById(R.id.bed); bed.setText(bed.getText() + " " + property.getNb_bedroom());
        TextView bath = myView.findViewById(R.id.bath); bath.setText(bath.getText() + " " + property.getNb_bathroom());
        TextView location = myView.findViewById(R.id.location); location.setText(location.getText() + property.getAddress());
        TextView interest = myView.findViewById(R.id.interest); interest.setText(interest.getText() + property.getProximity().get(0));
        TextView start_date = myView.findViewById(R.id.start_date); start_date.setText(start_date.getText() + " " + property.getStart_date());
        TextView status = myView.findViewById(R.id.status); status.setText(status.getText() + " " + property.getStatus() + soldate);
        TextView agent = myView.findViewById(R.id.agent); agent.setText(agent.getText() + property.getEstate_agent());

        ImageView sold = myView.findViewById(R.id.sold);

        int comparison = property.getStatus().compareTo("vendu");

        if (comparison != 0){
            sold.setVisibility(View.GONE);
        }

        if (property.getPriceIsDollar() == "Dollar") {
            money.setText(  money.getText() +  "$ " + String.valueOf(property.getPrice()));
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dollar, 0, 0, 0);


        }else{
            money.setText(money.getText() + String.valueOf(property.getPrice()) + " €");
            money.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_euro, 0, 0, 0);
        }




        Gallery gallery = (Gallery) myView.findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(mContext,property.getPhoto()));
        ImageView imageView = (ImageView) myView.findViewById(R.id.imageAvatar);

        Glide.with(mContext)
                //.load(property.getPhoto().get(0).getImage())
                .load("https://www.cheneaudiere.com/wp-content/uploads/2014/03/CHAMBRE-CHENEAUDIERE-%C2%AE-JEROME-MONDIERE-3-1.jpg")

                .into(imageView);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position,long id)
            {
                Toast.makeText(mContext,property.getPhoto().get(position).getDescript(),
                        Toast.LENGTH_SHORT).show();
                // display the images selected
                Glide.with(mContext)
                        .load(property.getPhoto().get(position).getImage())
                        .into(imageView);

            }
        });




        if (property.getVideo() != null) {
            recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            VideoAdapter videoAdapter = new VideoAdapter(property.getVideo());
            recyclerView.setAdapter(videoAdapter);
        }





        return myView;

    }

}
