package com.dev.userlogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    ArrayList<UserHelperClass> users;
    Context context;

    public RecAdapter(ArrayList<UserHelperClass> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlayout,parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, int position) {
        holder.name.setText(users.get(position).getUsername());
        holder.number.setText(users.get(position).getPhoneNo());
        String cc = users.get(position).getCC();
        Glide.with(context)
                .load(cc)
                .placeholder(R.drawable.flag)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.namee);
            number = itemView.findViewById(R.id.numberr);
            image = itemView.findViewById(R.id.imagee);
        }
    }
}
