package com.shubhamgupta16.simplewallpaper.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shubhamgupta16.simplewallpaper.R;

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);
        EditText edPasswordConfirm = findViewById(R.id.edPasswordConfirm);

        Button signUp = findViewById(R.id.btnSignIn);
        Intent intent = new Intent(this, MainActivity.class);
        signUp.setOnClickListener(view -> {

            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String passwordConfirm = edPasswordConfirm.getText().toString();

            if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
                Toast.makeText(this, "Do not empty any field", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.equals(passwordConfirm)) {
                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.pref_sign_up), Context.MODE_PRIVATE);
                String usernames = sharedPref.getString("usernames", "");
                String passwords = sharedPref.getString("passwords", "");

                List<String> usernamesList = Arrays.asList(usernames.split("~~~"));
                List<String> passwordList = Arrays.asList(passwords.split("~~~"));

                if (!usernamesList.contains(email)) {
                    usernames += email + "~~~";
                    passwords += password + "~~~";
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("usernames", usernames);
                    editor.putString("passwords", passwords);
                    editor.putBoolean("isLogged", true);
                    editor.apply();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Email is exist", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Password is not same", Toast.LENGTH_SHORT).show();
            }
        });

        TextView login = findViewById(R.id.loginAccount);
        login.setOnClickListener(view -> {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(new Intent(this, LoginActivity.class));
        });

    }
}
