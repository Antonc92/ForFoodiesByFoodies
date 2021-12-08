package com.example.forfoodiesbyfoodies.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfoodiesbyfoodies.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<User> mList;
    Context context;

    public RecyclerAdapter(Context context , ArrayList<User> mList){

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_users , parent ,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user1 = mList.get(position);
        holder.first.setText(user1.getFirst());
        holder.last.setText(user1.getLast());
        holder.username.setText(user1.getUsername());
        holder.email.setText(user1.getEmail());
        holder.acces.setText(user1.getAccess());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView first, last, username, email, acces;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            first = itemView.findViewById(R.id.FirstNameDisplayItem);
            last = itemView.findViewById(R.id.LAstNameDisplayItem);
            username = itemView.findViewById(R.id.UsernameDisplayItem);
            email = itemView.findViewById(R.id.EmailNameDisplayItem);
            acces = itemView.findViewById(R.id.AccessDisplayItem);
        }
    }
}