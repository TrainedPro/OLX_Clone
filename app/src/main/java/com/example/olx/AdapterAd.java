package com.example.olx;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.databinding.RowAdBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AdapterAd extends RecyclerView.Adapter<AdapterAd.HolderAd> implements Filterable {

    private Context context;

    public ArrayList<ModelAd> adArrayList;
    private ArrayList<ModelAd> filterList;

    private FilterAd filter;

    private RowAdBinding binding;

    public AdapterAd(Context context, ArrayList<ModelAd> adArrayList) {
        this.context = context;
        this.adArrayList = adArrayList;
        this.filterList = adArrayList;
    }

    @NonNull
    @Override
    public HolderAd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowAdBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderAd(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAd holder, int position) {

        ModelAd modelAd = adArrayList.get(position);

        String title = modelAd.getTitle();
        String description = modelAd.getDescription();
        String address = modelAd.getArea();
        String price = String.valueOf(modelAd.getPrice());

        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.addressTv.setText(address);
        holder.priceTv.setText(price);


    }

    @Override
    public int getItemCount() {
        return adArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new FilterAd(this, filterList);
        }

        return filter;
    }

    class HolderAd extends RecyclerView.ViewHolder {

        ShapeableImageView imageIv;
        TextView titleTv, descriptionTv, addressTv, priceTv;

        ImageButton favBtn;
        public HolderAd(@NonNull View itemView){
            super(itemView);

            imageIv = binding.imageIv;
            titleTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            favBtn = binding.favBtn;
            addressTv = binding.addressTv;
            priceTv = binding.priceTv;


        }
    }
}
