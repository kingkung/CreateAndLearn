package com.example.user.myapplication.coincollection;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.user.myapplication.R;

public class CoinCollectionActivity extends AppCompatActivity {

    private TextView coins;
    private TextView coinCompare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_collection_activity);
        getSupportActionBar().setTitle("Coin Collection");
        coins = findViewById(R.id.coins);
        coinCompare = findViewById(R.id.compareCoins);

        CoinCollection myCoins = new CoinCollection();

        myCoins.addCoin(new Coin(1964, 25, "Fine", 2500000));
        myCoins.addCoin(new Coin(1982, 1, "Mint State", 5000000));
        myCoins.addCoin(new Coin(1930, 10, "Good", 5000));

        coins.setText(myCoins.toString());

        coinCompare.setText("Coin 1 is better than Coin 2: " + myCoins.getCoin(1).isBetter(myCoins.getCoin(2)));
    }
}
