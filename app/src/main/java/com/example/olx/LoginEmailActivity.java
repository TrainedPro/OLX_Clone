package com.example.olx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.olx.databinding.ActivityLoginEmailBinding;

interface Callback {
    void onSuccess(String authToken);
    void onFailure(String errorMessage);
}

public class LoginEmailActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ActivityLoginEmailBinding binding;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginEmailBinding.inflate(getLayoutInflater());
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
                startActivity(new Intent(LoginEmailActivity.this, RegisterEmailActivity.class));
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
        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid Email");
            binding.emailEt.requestFocus();
        } else if (password.isEmpty()) {
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else {
            loginUser(new Callback() {
                @Override
                public void onSuccess(String authToken) {
                    sessionManager.saveAuthToken(authToken);
                    startActivity(new Intent(LoginEmailActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Utils.toast(LoginEmailActivity.this, errorMessage);
                }
            });
        }
    }

    private void loginUser(final Callback callback) {
        progressDialog.setMessage("Logging In");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Simulate login success or failure
                        Log.d("Login", "Email: " + binding.emailEt.getText().toString().trim() + ", Password: " + binding.passwordEt.getText().toString().trim());
                        Utils.User isLoginSuccessful = Utils.loginUser(binding.emailEt.getText().toString().trim(), binding.passwordEt.getText().toString().trim());
                        progressDialog.dismiss();
                        if (isLoginSuccessful != null) {
                            String authToken = String.valueOf(isLoginSuccessful.getId()); // Replace with actual auth token
                            callback.onSuccess(authToken);
                        } else {
                            callback.onFailure("Invalid email or password");
                        }
                    }
                }, 2000);
    }


    private void updateUserInfo(){
        progressDialog.setMessage("Saving User Info");
    }
}