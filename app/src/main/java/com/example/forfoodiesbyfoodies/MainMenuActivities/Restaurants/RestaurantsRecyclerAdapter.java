package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantsRecyclerAdapter extends RecyclerView.ViewHolder {


    View mView;


    public RestaurantsRecyclerAdapter(View itemView) {
        super(itemView);

        mView = itemView;
    }


    public void setDetails(Context ctx, String name, String description, String street, String photo) {

        TextView RestaurantName, RestaurantDescription, RestaurantStreet;
        ImageView RestaurantImage;

        RestaurantName = mView.findViewById(R.id.RestaurantNameItemTextView);
        RestaurantDescription = mView.findViewById(R.id.RestaurandDescriptionItemTextView);
        RestaurantImage = mView.findViewById(R.id.RestaurantImageCardView);
        RestaurantStreet = mView.findViewById(R.id.RestaurantStreetTextViewItem);

        RestaurantName.setText(name);
        RestaurantDescription.setText(description);
        RestaurantStreet.setText(street);
        Picasso.get().load(photo).into(RestaurantImage);

    }
}
