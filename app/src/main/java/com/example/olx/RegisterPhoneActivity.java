package com.example.olx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.olx.databinding.ActivityRegisterPhoneBinding;

public class RegisterPhoneActivity extends AppCompatActivity {

    private ActivityRegisterPhoneBinding binding;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterPhoneBinding.inflate(getLayoutInflater());
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

        binding.haveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        String phone = binding.phoneEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();
        String cPassword = binding.cPasswordEt.getText().toString().trim();

        if(!Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneEt.setError("Invalid Phone");
            binding.phoneEt.requestFocus();
        } else if (password.isEmpty()) {
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else if(!password.equals(cPassword)) {
            binding.passwordEt.setError("Password Doesn't Match");
            binding.passwordEt.requestFocus();
        }
        else {
            registerUser(new Callback() {
                @Override
                public void onSuccess(String authToken) {
                    sessionManager.saveAuthToken(authToken);
                    startActivity(new Intent(RegisterPhoneActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Utils.toast(RegisterPhoneActivity.this, errorMessage);
                }
            });
        }
    }

    private void registerUser(Callback callback) {
        progressDialog.setMessage("Creating Account");
        progressDialog.show();

        // TODO bool signUpWithPhone(phone, password). False indicating phone is used
        boolean isRegistrationSuccessful = true;
        progressDialog.dismiss();

        if (isRegistrationSuccessful) {
            String authToken = "example_auth_token"; // Replace with actual auth token
            callback.onSuccess(authToken);
        } else {
            callback.onFailure("Invalid phone or password");
        }


    }
}