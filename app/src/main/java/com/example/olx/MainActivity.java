package com.example.olx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.olx.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SessionManager sessionManager;
    private int currentFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = SessionManager.getInstance(this);

        if (Utils.getAllUsers().isEmpty()) {
            sessionManager.clearAuthToken();
        }

        Log.d("Main", "In Main");

        int intentFragment = getIntent().getIntExtra("frgToLoad", 1);
        Log.d("Fragment Load", String.valueOf(intentFragment));

        switch (intentFragment) {
            case 2:
                showChatsFragment();
                break;
            case 3:
                showFavFragment();
                break;
            case 4:
                showAccountFragment();
                break;
            default:
                showHomeFragment();
        }

        binding.bottomNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_home) {
                    showHomeFragment();
                } else if (itemId == R.id.menu_chats) {
                    if (!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        currentFragmentId = R.id.menu_chats;
                        return false;
                    }
                    showChatsFragment();
                } else if (itemId == R.id.menu_fav) {
                    if (!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        currentFragmentId = R.id.menu_fav;
                        return false;
                    }
                    showFavFragment();
                } else if (itemId == R.id.menu_account) {
                    if (!sessionManager.isLoggedIn()) {
                        Utils.toast(MainActivity.this, "Login Required");
                        startLoginOptions();
                        currentFragmentId = R.id.menu_account;
                        return false;
                    }
                    showAccountFragment();
                }
                return true;
            }
        });

        if (!sessionManager.isLoggedIn()) {
            startLoginOptions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (currentFragmentId == R.id.menu_chats) {
                showChatsFragment();
            } else if (currentFragmentId == R.id.menu_fav) {
                showFavFragment();
            } else if (currentFragmentId == R.id.menu_account) {
                showAccountFragment();
            } else {
                showHomeFragment();
            }

        } else if (resultCode == RESULT_CANCELED) {
            showHomeFragment();
        }
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
        Intent intent = new Intent(this, LoginOptionsActivity.class);
        startActivityForResult(intent, 1);
    }
}
