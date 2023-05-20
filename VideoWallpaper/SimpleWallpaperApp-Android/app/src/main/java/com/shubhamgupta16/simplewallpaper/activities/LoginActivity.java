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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.shubhamgupta16.simplewallpaper.R;
import com.shubhamgupta16.simplewallpaper.data_source.DataService;
import com.shubhamgupta16.simplewallpaper.fragments.WallsFragment;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);

        Button signUp = findViewById(R.id.btnSignIn);
        Intent intent = new Intent(this, MainActivity.class);
        signUp.setOnClickListener(view -> {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Do not empty any field", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.pref_sign_up), Context.MODE_PRIVATE);
            String usernames = sharedPref.getString("usernames", "");
            String passwords = sharedPref.getString("passwords", "");

            List<String> usernamesList = Arrays.asList(usernames.split("~~~"));
            List<String> passwordList = Arrays.asList(passwords.split("~~~"));

            if (usernamesList.contains(email) && passwordList.contains(password)) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("isLogged", true);
                editor.apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Email is not exist", Toast.LENGTH_SHORT).show();
            }
        });

        TextView login = findViewById(R.id.createAccount);
        login.setOnClickListener(view -> {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(new Intent(this, SignUpActivity.class));
        });

    }
}
