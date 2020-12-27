package com.example.user.myapplication.foodorders;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;

public class FoodOrdersActivity extends AppCompatActivity {

    private TextView totalBill;
    private TextView orderSummary;
    private FoodOrder foodOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_orders_activity);
        getSupportActionBar().setTitle("The Food Place!");
        foodOrder = new FoodOrder();
        totalBill = findViewById(R.id.total_bill);
        orderSummary = findViewById(R.id.order_summary);
        findViewById(R.id.bread).setOnClickListener((v) -> {
            foodOrder.addBread(1);
            refreshTotals();
        });
        findViewById(R.id.salad).setOnClickListener((v) -> {
            foodOrder.addSalad(1);
            refreshTotals();
        });
        findViewById(R.id.pizza).setOnClickListener((v) -> {
            foodOrder.addPizza(1);
            refreshTotals();
        });
        findViewById(R.id.fries).setOnClickListener((v) -> {
            foodOrder.addFries(1);
            refreshTotals();
        });
        findViewById(R.id.soda).setOnClickListener((v) -> {
            foodOrder.addSoda(1);
            refreshTotals();
        });
        findViewById(R.id.clear_order).setOnClickListener((v) -> {
            foodOrder.completeOrder();
            refreshTotals();
        });
        findViewById(R.id.complete_order).setOnClickListener((v) -> {
            EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                 .setView(input)
                    .setTitle("How much cash do you have?")
                    .setPositiveButton("Done", (dialog, which) -> {
                        try {
                            double cash = Double.parseDouble(input.getText().toString());
                            if (cash >= foodOrder.getValue()) {
                                Toast.makeText(this, "Your change is $" + (cash - foodOrder.getValue()), Toast.LENGTH_LONG).show();
                                foodOrder.completeOrder();
                                refreshTotals();
                            } else {
                                Toast.makeText(this, "You don't have enough money", Toast.LENGTH_LONG).show();
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "Not a valid price", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        refreshTotals();
    }

    private void refreshTotals() {
        totalBill.setText("Total = " + foodOrder.getStringValue());
        orderSummary.setText(foodOrder.toString());
    }
}
