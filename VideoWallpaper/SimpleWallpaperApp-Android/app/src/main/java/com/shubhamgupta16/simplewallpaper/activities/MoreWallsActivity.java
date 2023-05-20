package com.shubhamgupta16.simplewallpaper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.shubhamgupta16.simplewallpaper.R;
import com.shubhamgupta16.simplewallpaper.data_source.DataService;
import com.shubhamgupta16.simplewallpaper.fragments.WallsFragment;

public class MoreWallsActivity extends AppCompatActivity {

    private WallsFragment wallsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_walls);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());


        Intent intent = getIntent();
        FragmentManager manager = getSupportFragmentManager();
        wallsFragment = (WallsFragment) manager.findFragmentById(R.id.moreFragment);
        if (intent.hasExtra("category")) {
            String category = intent.getStringExtra("category");
            toolbar.setTitle(category);
            assert wallsFragment != null;
            wallsFragment.setFragment(DataService.QueryType.CATEGORY, category);
        } else {
            finish();
        }

    }

    @Override
    protected void onStart() {
        wallsFragment.focus();
        super.onStart();
    }

}