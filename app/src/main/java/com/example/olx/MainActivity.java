package com.example.olx;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.olx.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = SessionManager.getInstance(this);
//        sessionManager.clearAuthToken();

        if(!sessionManager.isLoggedIn()) {
            startLoginOptions();
        }

        showHomeFragment();

        binding.bottomNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_home){

                    showHomeFragment();

                    return true;

                } else if (itemId == R.id.menu_chats) {

                    if(!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        return false;
                    } else {
                        showChatsFragment();
                        return true;
                    }


                } else if (itemId == R.id.menu_fav) {
                    if(!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        return false;
                    } else {
                        showFavFragment();
                        return true;
                    }

                } else if (itemId == R.id.menu_account){
                    if(!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        return false;
                    } else {
                        showAccountFragment();
                        return true;
                    }
                } else {
                    return false;

                }

            }
        });


    }

    private void showHomeFragment() {
        binding.toolbarTitleTv.setText("Home");

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    private void showChatsFragment() {
        binding.toolbarTitleTv.setText("Chats");

        ChatsFragment fragment = new ChatsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "ChatsFragment");
        fragmentTransaction.commit();
    }

    private void showFavFragment() {
        binding.toolbarTitleTv.setText("Favourite");

        FavFragment fragment = new FavFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "FavFragment");
        fragmentTransaction.commit();
    }

    private void showAccountFragment() {
        binding.toolbarTitleTv.setText("Account");

        AccountFragment fragment = new AccountFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "AccountFragment");
        fragmentTransaction.commit();
    }

    private void startLoginOptions() {
        startActivity(new Intent(this, LoginOptionsActivity.class));
    }





}