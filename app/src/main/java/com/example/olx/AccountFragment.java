package com.example.olx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olx.databinding.ActivityMainBinding;
import com.example.olx.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private Context mContext;

    private SessionManager sessionManager;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(LayoutInflater.from(mContext), container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = SessionManager.getInstance(mContext);

        loadUserInfo();

        binding.logoutCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearAuthToken();
                startActivity(new Intent(mContext, MainActivity.class));
                getActivity().finishAffinity();
            }
        });

        binding.editProfileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ProfileEditActivity.class));
            }
        });
    }

    private void loadUserInfo() {
        // TODO: retrieve user info
        // hashMap<String, String> getUserInfo(int ID)
        // returns id, name, email, phone, email

        int id;
        String name = "Hassaan Anwar";
        String email = "p229160@pwr.nu.edu.pk";
        String phone = null;

        binding.nameTv.setText(name);
        binding.emailTv.setText(email);
        binding.phoneTv.setText(phone);
    }
}