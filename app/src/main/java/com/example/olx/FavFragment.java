package com.example.olx;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olx.databinding.FragmentFavBinding;

import java.util.ArrayList;

public class FavFragment extends Fragment {
    private Context mContext;

    private ArrayList<ModelAd> favAdArrayList;

    private AdapterAd adapterAd;

    FragmentFavBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(LayoutInflater.from(mContext), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFavAds();

    }


    public void loadFavAds() {
        favAdArrayList = new ArrayList<>();

        ArrayList<ModelAd> products = Utils.getProducts();

        for (ModelAd product : products) {
            if (product.isFavourite(Integer.parseInt(SessionManager.getInstance(mContext).getAuthString()))) {
                favAdArrayList.add(product);
            }
        }

        adapterAd = new AdapterAd(mContext, favAdArrayList);
        binding.favAdsRv.setAdapter(adapterAd);
    }
}
