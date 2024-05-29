package com.example.olx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.example.olx.databinding.ActivityRegisterEmailBinding;

public class RegisterEmailActivity extends AppCompatActivity {

    private ActivityRegisterEmailBinding binding;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterEmailBinding.inflate(getLayoutInflater());
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
        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();
        String cPassword = binding.cPasswordEt.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid Email");
            binding.emailEt.requestFocus();
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
                    startActivity(new Intent(RegisterEmailActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Utils.toast(RegisterEmailActivity.this, errorMessage);
                }
            });
        }
    }

    private void registerUser(Callback callback) {
        progressDialog.setMessage("Creating Account");
        progressDialog.show();

        String name = binding.nameEt.getText().toString().trim();
        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();


        Utils.User isRegistrationSuccessful = Utils.registerUser(name, email, password, null);
        Log.d("Signin", "Email: " + email + ", Password: " + password);

        progressDialog.dismiss();

        if (isRegistrationSuccessful != null) {
            String authToken = String.valueOf(isRegistrationSuccessful.getId()); // Replace with actual auth token
            callback.onSuccess(authToken);
        } else {
            callback.onFailure("Invalid email or password");
        }


    }
}