package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.myapplication.caesarcipher.CaesarCipherActivity;
import com.example.user.myapplication.coincollection.CoinCollectionActivity;
import com.example.user.myapplication.colormatchgame.ColorMatchGameActivity;
import com.example.user.myapplication.foodorders.FoodOrdersActivity;
import com.example.user.myapplication.huntthewumpus.HuntTheWumpusActivity;
import com.example.user.myapplication.lunchcombo.RestaurantActivity;
import com.example.user.myapplication.magic8ball.Magic8BallActivity;
import com.example.user.myapplication.memorygame.MemoryGameActivity;
import com.example.user.myapplication.tendotfive.TenDotFiveActivity;
import com.example.user.myapplication.trivia.TriviaActivity;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final ImmutableMap<String, Class> LESSONS =
            new ImmutableMap.Builder<String, Class>()
                    .put("Ten Dot Five", TenDotFiveActivity.class)
                    .put("Hunt The Wumpus", HuntTheWumpusActivity.class)
                    .put("Memory Game", MemoryGameActivity.class)
                    .put("Color Match Game", ColorMatchGameActivity.class)
                    .put("Session 1 - Trivia", TriviaActivity.class)
                    .put("Session 2 - Magic 8-Ball", Magic8BallActivity.class)
                    .put("Session 3 - Coin Collection", CoinCollectionActivity.class)
                    .put("Session 4 - Caesar Cipher", CaesarCipherActivity.class)
                    .put("Session 5 - Food Orders", FoodOrdersActivity.class)
                    .put("Session 8 - Lunch Combo", RestaurantActivity.class)
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Java and Android Projects");
        setContentView(R.layout.main_activity);
        ViewGroup viewGroup = findViewById(R.id.lesson_launcher);
        for (Map.Entry<String, Class> entry : LESSONS.entrySet()) {
            Button button = (Button) getLayoutInflater().inflate(
                    R.layout.project_button, viewGroup, false);
            button.setText(entry.getKey());
            button.setOnClickListener(
                    (v) -> startActivity(new Intent(this, entry.getValue())));
            viewGroup.addView(button);
        }
    }
}
