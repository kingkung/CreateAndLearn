package com.example.user.myapplication.memorygame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.user.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemoryGameActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private GridLayout gridLayout;
    private View youWin;
    private View newGame;
    private int[] cards = {
            R.drawable.bulbasaur,
            R.drawable.bulbasaur,
            R.drawable.charmander,
            R.drawable.charmander,
            R.drawable.eevee,
            R.drawable.eevee,
            R.drawable.meowth,
            R.drawable.meowth,
            R.drawable.mouse,
            R.drawable.mouse,
            R.drawable.pikachu,
            R.drawable.pikachu,
            R.drawable.psyduck,
            R.drawable.psyduck,
            R.drawable.snorlax,
            R.drawable.snorlax,
    };
    private boolean disableClicks;
    private List<Integer> deck;
    private Set<Integer> revealed = new HashSet<>();

    private int prevIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.memory_game_activity);
        gridLayout = findViewById(R.id.grid);
        youWin = findViewById(R.id.you_win);
        newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener((view) -> startNewGame());
        startNewGame();
    }

    private void startNewGame() {
        youWin.setVisibility(View.INVISIBLE);
        newGame.setEnabled(false);
        int numCards = gridLayout.getColumnCount() * gridLayout.getRowCount();
        deck = new ArrayList<>();
        for (int card : cards) {
            deck.add(card);
        }
        Collections.shuffle(deck);
        revealed.clear();
        prevIndex = -1;
        gridLayout.removeAllViews();
        for (int i = 0; i < numCards; i++) {
            final int index = i;
            ImageView view = (ImageView) getLayoutInflater().inflate(
                    R.layout.memory_card, gridLayout, false);
            view.setOnClickListener((v) -> revealCard(view, index));
            gridLayout.addView(view);
        }
    }

    private void revealCard(ImageView view, int index) {
        if (disableClicks || prevIndex == index || revealed.contains(index)) {
            return;
        }
        view.setImageResource(deck.get(index));
        if (prevIndex == -1) {
            prevIndex = index;
            return;
        }
        if (deck.get(prevIndex).equals(deck.get(index))) {
            revealed.add(index);
            revealed.add(prevIndex);
            prevIndex = -1;
            if (revealed.size() == deck.size()) {
                youWin.setVisibility(View.VISIBLE);
                newGame.setEnabled(true);
            }
        } else {
            disableClicks = true;
            handler.postDelayed((Runnable) () -> {
                ((ImageView) gridLayout.getChildAt(prevIndex))
                        .setImageResource(R.drawable.card_back);
                ((ImageView) gridLayout.getChildAt(index))
                        .setImageResource(R.drawable.card_back);
                prevIndex = -1;
                disableClicks = false;
            }, 2500);
        }
    }
}
