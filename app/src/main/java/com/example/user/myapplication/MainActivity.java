package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.myapplication.caesarcipher.CaesarCipherActivity;
import com.example.user.myapplication.coincollection.CoinCollectionActivity;
import com.example.user.myapplication.magic8ball.Magic8BallActivity;
import com.example.user.myapplication.foodorders.FoodOrdersActivity;
import com.example.user.myapplication.trivia.TriviaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setTitle("Java and Android Projects");
        findViewById(R.id.session1).setOnClickListener(
                (v) -> launchProject(TriviaActivity.class));
        findViewById(R.id.session2).setOnClickListener(
                (v) -> launchProject(Magic8BallActivity.class));
        findViewById(R.id.session3).setOnClickListener(
                (v) -> launchProject(CoinCollectionActivity.class));
        findViewById(R.id.session4).setOnClickListener(
                (v) -> launchProject(CaesarCipherActivity.class));
        findViewById(R.id.session5).setOnClickListener(
                (v) -> launchProject(FoodOrdersActivity.class));
    }

    private void launchProject(Class className) {
        startActivity(new Intent(this, className));
    }
}
