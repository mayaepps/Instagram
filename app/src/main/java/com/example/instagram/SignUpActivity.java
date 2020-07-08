package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagram.databinding.ActivityLoginBinding;
import com.example.instagram.databinding.ActivitySignUpBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = binding.etSignupUsername.getText().toString();
                String email = binding.etSignupEmail.getText().toString();
                String password = binding.etSignupPassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(username, email, password);
                }
            }
        });
    }

    private void signUp(String username, String email, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong and tell user
                    Toast.makeText(SignUpActivity.this, "Could not create account: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
         });
    }

}