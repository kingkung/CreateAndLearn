package com.example.user.myapplication.tenandahalf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TenAndAHalfActivity extends AppCompatActivity {

    private static final ImmutableList<Card> DECK = ImmutableList.of(
            new Card(R.drawable.spade2, 2),
            new Card(R.drawable.spade3, 3),
            new Card(R.drawable.spade5, 5),
            new Card(R.drawable.spade4, 4),
            new Card(R.drawable.spade6, 6),
            new Card(R.drawable.spade7, 7),
            new Card(R.drawable.spade8, 8),
            new Card(R.drawable.spade9, 9),
            new Card(R.drawable.spade10, 10),
            new Card(R.drawable.spade_jack, 0.5),
            new Card(R.drawable.spade_queen, 0.5),
            new Card(R.drawable.spade_king, 0.5),
            new Card(R.drawable.spade_ace, 1));
    private static final double MAX = 10.5;
    private static final int DRAGON = 5;

    private ViewGroup yourCards;
    private ViewGroup dealerCards;
    private TextView yourScore;
    private TextView dealerScore;
    private ViewGroup actions;
    private Button hit;
    private Button stay;
    private TextView result;
    private Button newGame;

    private Handler handler = new Handler(Looper.getMainLooper());
    private double scorePlayer;
    private double scoreDealer;
    private List<Card> deck = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ten_and_a_half_activity);
        actions = findViewById(R.id.actions);
        actions.setVisibility(View.INVISIBLE);
        hit = findViewById(R.id.hit);
        hit.setOnClickListener((v) -> dealYou());
        stay = findViewById(R.id.stay);
        stay.setOnClickListener((v) -> startDealingDealer());
        yourCards = findViewById(R.id.player_cards);
        yourScore = findViewById(R.id.player_score);
        dealerCards = findViewById(R.id.dealer_cards);
        dealerScore = findViewById(R.id.dealer_score);
        result = findViewById(R.id.you_win);
        result.setVisibility(View.INVISIBLE);
        newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener((v) -> startNewGame());
    }

    private void startNewGame() {
        newGame.setEnabled(false);
        scorePlayer = 0;
        scoreDealer = 0;
        result.setVisibility(View.INVISIBLE);
        yourCards.removeAllViews();
        dealerCards.removeAllViews();
        yourScore.setText(getScoreText(scorePlayer));
        dealerScore.setText(getScoreText(scoreDealer));
        deck.clear();
        deck.addAll(DECK);
        actions.setVisibility(View.VISIBLE);
        hit.setEnabled(true);
        stay.setEnabled(true);
        Collections.shuffle(deck);
        dealDealer(false);
        dealYou();
    }

    private void startDealingDealer() {
        hit.setEnabled(false);
        stay.setEnabled(false);
        dealerMove();
    }

    private void dealerMove() {
        handler.postDelayed(() -> {
            if (scoreDealer > MAX) {
                youWin();
            } else if (dealerCards.getChildCount() == 5) {
                youLose();
            } else if (scoreDealer > scorePlayer) {
                youLose();
            } else {
                dealDealer(true);
            }
        }, 2500);
    }

    private void dealDealer(boolean dealAgain) {
        Card card = deck.remove(0);
        ImageView cardView =
                (ImageView) LayoutInflater.from(this).inflate(R.layout.poker_card, dealerCards, false);
        cardView.setImageResource(card.imageId);
        if (yourCards.getChildCount() > 0) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.overlap);
        }
        dealerCards.addView(cardView);
        scoreDealer += card.value;
        dealerScore.setText(getScoreText(scoreDealer));
        if (dealAgain) {
            dealerMove();
        }
    }

    private void dealYou() {
        Card card = deck.remove(0);
        ImageView cardView =
                (ImageView) LayoutInflater.from(this).inflate(R.layout.poker_card, yourCards, false);
        cardView.setImageResource(card.imageId);
        if (yourCards.getChildCount() > 0) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.overlap);
        }
        yourCards.addView(cardView);
        scorePlayer += card.value;
        yourScore.setText(getScoreText(scorePlayer));

        if (scorePlayer > MAX) {
            youLose();
        } else if (yourCards.getChildCount() == DRAGON) {
            youWin();
        }
    }

    private void youLose() {
        result.setVisibility(View.VISIBLE);
        result.setText("You Lose!");
        actions.setVisibility(View.INVISIBLE);
        newGame.setEnabled(true);
    }

    private void youWin() {
        result.setVisibility(View.VISIBLE);
        result.setText("You Win!");
        actions.setVisibility(View.INVISIBLE);
        newGame.setEnabled(true);
    }

    private String getScoreText(double d) {
        return String.format("%.1f", d);
    }

    private static class Card {
        @DrawableRes final int imageId;
        final double value;

        Card (@DrawableRes int imageId, double value) {
            this.imageId = imageId;
            this.value = value;
        }
    }
}
