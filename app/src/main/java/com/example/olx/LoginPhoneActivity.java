package com.example.olx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.olx.databinding.ActivityLoginPhoneBinding;


public class LoginPhoneActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ActivityLoginPhoneBinding binding;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = SessionManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPhoneActivity.this, RegisterPhoneActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        String phone = binding.phoneEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        if(!Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneEt.setError("Invalid Phone");
            binding.phoneEt.requestFocus();
        } else if (password.isEmpty()) {
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else {
            loginUser(new Callback() {
                @Override
                public void onSuccess(String authToken) {
                    sessionManager.saveAuthToken(authToken);
                    startActivity(new Intent(LoginPhoneActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Utils.toast(LoginPhoneActivity.this, errorMessage);
                }
            });
        }
    }

    private void loginUser(final Callback callback) {
        progressDialog.setMessage("Logging In");
        progressDialog.show();

        // TODO: (replace this with your actual login logic)
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Simulate login success or failure
                        boolean isLoginSuccessful = true; // Change this to actual login result
                        progressDialog.dismiss();
                        if (isLoginSuccessful) {
                            String authToken = "example_auth_token"; // Replace with actual auth token
                            callback.onSuccess(authToken);
                        } else {
                            callback.onFailure("Invalid phone or password");
                        }
                    }
                }, 2000);
    }


    private void updateUserInfo(){
        progressDialog.setMessage("Saving User Info");
    }
}