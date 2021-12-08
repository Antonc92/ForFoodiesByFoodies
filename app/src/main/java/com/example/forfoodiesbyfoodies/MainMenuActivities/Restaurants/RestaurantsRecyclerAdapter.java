package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantsRecyclerAdapter extends RecyclerView.Adapter<RestaurantsRecyclerAdapter.ViewHolder> {
	
	private ItemClickListener itemClickListener;
	private ArrayList<Restaurants> restaurantList;
	
	public RestaurantsRecyclerAdapter(ArrayList<Restaurants> restaurantList, ItemClickListener itemClickListener){
		this.itemClickListener = itemClickListener;
		this.restaurantList = restaurantList;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(View.inflate(parent.getContext(), R.layout.list_item_restaurants, null));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
		
		holder.RestaurantName.setText(restaurantList.get(position).name);
		holder.RestaurantDescription.setText(restaurantList.get(position).description);
		holder.RestaurantStreet.setText(restaurantList.get(position).street);
		Picasso.get().load(restaurantList.get(position).photo).into(holder.RestaurantImage);
		
		holder.rootLayout.setOnClickListener(v -> {
			if(itemClickListener != null) itemClickListener.onClick(position);
		});
	}
	
	@Override
	public int getItemCount() {
		return restaurantList.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{

        TextView RestaurantName, RestaurantDescription, RestaurantStreet;
        ImageView RestaurantImage;
        ConstraintLayout rootLayout;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			
			RestaurantName = itemView.findViewById(R.id.RestaurantNameItemTextView);
			RestaurantDescription = itemView.findViewById(R.id.RestaurandDescriptionItemTextView);
			RestaurantImage = itemView.findViewById(R.id.RestaurantImageCardView);
			RestaurantStreet = itemView.findViewById(R.id.RestaurantStreetTextViewItem);
			rootLayout = itemView.findViewById(R.id.parentLayout);
		}
	}
	
	public interface ItemClickListener{
		void onClick(int position);
	}
}
