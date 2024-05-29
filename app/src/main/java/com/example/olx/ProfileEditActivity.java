package com.example.olx;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.olx.databinding.ActivityProfileEditBinding;

import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private ActivityProfileEditBinding binding;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadUserInfo();

        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.profileImagePickFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

    }

    private void deleteUser() {
        String confirmation = binding.deleteEt.getText().toString().trim();

        if(confirmation.equals("CONFIRM")) {
            Utils.deleteUser(Integer.parseInt(SessionManager.getInstance(this).getAuthToken()));
            SessionManager sessionManager = SessionManager.getInstance(this);
            sessionManager.clearAuthToken();
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        } else {
            Utils.toast(this, "Incorrect Confirmation");
            binding.deleteEt.setText("TYPE 'CONFIRM'");
        }
    }


    private void updateUserInfo() {
        String name = binding.nameEt.getText().toString().trim();
        String email = binding.emailEt.getText().toString().trim();
        String phone = binding.phoneEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        Log.d("Update Password", "Password: " + password);

        // BUG: Account can exist without email and phone
        if(email.isEmpty()) email = null;
        if(phone.isEmpty()) phone = null;
        if(password.isEmpty()) password = null;

        progressDialog.setMessage("Updating Info");
        progressDialog.show();

        Utils.setUserInfo(Integer.parseInt(SessionManager.getInstance(this).getAuthToken()), name, email, phone, password);

        progressDialog.dismiss();
        Utils.toast(ProfileEditActivity.this, "Profile Updated");

        finish();
        startActivity(new Intent(this, MainActivity.class).putExtra("frgToLoad", 4));
    }

    private void loadUserInfo() {
        Utils.User user = Utils.getUser(Integer.parseInt(SessionManager.getInstance(this).getAuthToken()));

        String name = user.getUserName();
        String email = user.getEmail();
        String phone = user.getPhone();

        binding.nameEt.setText(name);
        binding.emailEt.setText(email);
        binding.phoneEt.setText(phone);
    }

    private void imagePickDialog() {
        PopupMenu popupMenu = new PopupMenu(this, binding.profileImagePickFab);

        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Camera");
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Gallery");

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == 1){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestCameraPermission.launch(new String[]{Manifest.permission.CAMERA});
                    } else {
                        requestCameraPermission.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                    }
                } else if (itemId == 2) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        requestStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }

                }
                return false;
            }
        });
    }

    private ActivityResultLauncher<String[]> requestCameraPermission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    boolean areAllGranted = true;

                    for(Boolean isGranted: result.values()) {
                        areAllGranted = areAllGranted && isGranted;
                    }

                    if(!areAllGranted) {
                        Utils.toast(ProfileEditActivity.this, "Camera and/or Storage Permissions Denied");
                    }
                }
            }
    );

    private ActivityResultLauncher<String> requestStoragePermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if(!isGranted){
                        Utils.toast(ProfileEditActivity.this, "Storage Permissions Denied");
                    }

                }
            }
    );

}