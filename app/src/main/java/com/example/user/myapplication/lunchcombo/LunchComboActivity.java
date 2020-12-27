package com.example.user.myapplication.lunchcombo;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.example.user.myapplication.R;

public class LunchComboActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_combo_activity);
        findViewById(R.id.backButton).setOnClickListener((view) -> finish());
        LunchCombo combo = new LunchMenu().createCombo();
        TextView order = findViewById(R.id.order);
        order.setText(combo.toString());
        TextView total = findViewById(R.id.total);
        total.setText(String.format("Total: $%.2f", combo.getTotal()));
    }
}
