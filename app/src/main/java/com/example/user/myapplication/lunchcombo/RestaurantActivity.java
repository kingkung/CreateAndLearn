package com.example.user.myapplication.lunchcombo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.user.myapplication.R;

public class RestaurantActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);
        findViewById(R.id.lunchButton).setOnClickListener(this::launchLunchComboActivity);
    }

    void launchLunchComboActivity(View v) {
        Intent intent = new Intent(this, LunchComboActivity.class);
        startActivity(intent);
    }
}
