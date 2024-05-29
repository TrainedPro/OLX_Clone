package com.example.olx;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olx.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private Context mContext;

    private ArrayList<ModelAd> adArrayList;

    private AdapterAd adapterAd;

    FragmentHomeBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(mContext), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadCategories();

        loadAds("All");

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String query = s.toString();
                    adapterAd.getFilter().filter(query);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void loadCategories() {
        ArrayList<ModelCategory> categoryArrayList = new ArrayList<>();

        ModelCategory modelCategoryAll = new ModelCategory("All", R.drawable.ic_all_white);
        categoryArrayList.add(modelCategoryAll);

        String[] categories = Utils.getCategories();
        for(int i = 0; i < Objects.requireNonNull(categories).length; i++){
            ModelCategory modelCategory = new ModelCategory(categories[i], R.drawable.ic_phone_white);
            categoryArrayList.add(modelCategory);
        }

        AdapterCategory adapterCategory = new AdapterCategory(mContext, categoryArrayList, new RvListenerCategory() {
            @Override
            public void onCategoryClick(ModelCategory modelCategory) {
                loadAds(modelCategory.getCategory());
            }
        });

        binding.categoriesRv.setAdapter(adapterCategory);
    }

    private void loadAds(String category) {
        adArrayList = new ArrayList<>();

        ArrayList<ModelAd> products = Utils.getProducts();

        for(int i = 0 ; i < products.size(); i++) {
            if(category.equals("All")) {
                adArrayList.add(products.get(i));
            } else {
                if(category.equals(products.get(i).getCategory())){
                adArrayList.add(products.get(i));
                }
            }
        }

        adapterAd = new AdapterAd(mContext, adArrayList);
        binding.adsRv.setAdapter(adapterAd);

    }
}