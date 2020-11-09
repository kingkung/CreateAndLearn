package com.example.user.myapplication.tenandahalf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class TenAndAHalfActivity extends AppCompatActivity implements GameController.GameEventCallback {

    private final long DEALER_MOVE_DELAY_IN_MILLIS = 2500;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final GameController gameController = new GameController(this);

    private ViewGroup playerHandLayout;
    private ViewGroup dealerHandLayout;
    private TextView playerScoreTextView;
    private TextView dealerScoreTextView;
    private TextView gameResultTextView;
    private ViewGroup buttonContainer;
    private Button hitButton;
    private Button stayButton;
    private Button newGameButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ten_and_a_half_activity);
        buttonContainer = findViewById(R.id.actions);
        buttonContainer.setVisibility(View.INVISIBLE);
        hitButton = findViewById(R.id.hit);
        hitButton.setOnClickListener((v) -> {
            gameController.dealCardToYou();
        });
        stayButton = findViewById(R.id.stay);
        stayButton.setOnClickListener((v) -> endYourTurn());
        playerHandLayout = findViewById(R.id.player_cards);
        playerScoreTextView = findViewById(R.id.player_score);
        dealerHandLayout = findViewById(R.id.dealer_cards);
        dealerScoreTextView = findViewById(R.id.dealer_score);
        gameResultTextView = findViewById(R.id.you_win);
        gameResultTextView.setVisibility(View.INVISIBLE);
        newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener((v) -> startNewGame());
    }

    private void startNewGame() {
        gameController.resetGameState();
        newGameButton.setEnabled(false);
        gameResultTextView.setVisibility(View.INVISIBLE);
        playerHandLayout.removeAllViews();
        dealerHandLayout.removeAllViews();
        playerScoreTextView.setText(gameController.getPlayerScore());
        dealerScoreTextView.setText(gameController.getDealerScore());
        buttonContainer.setVisibility(View.VISIBLE);
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        gameController.dealCardToDealer();
        gameController.dealCardToYou();
    }

    private void endYourTurn() {
        gameController.endYourTurn();
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        makeDealerMove();
    }

    private void makeDealerMove() {
        handler.postDelayed(() -> {
            boolean gameHasEnded = gameController.checkIfGameHasEnded();
            if (!gameHasEnded) {
                gameController.dealCardToDealer();
            }
        }, DEALER_MOVE_DELAY_IN_MILLIS);
    }

    @Override
    public void onPlayerWin() {
        handler.removeCallbacksAndMessages(null);
        gameResultTextView.setVisibility(View.VISIBLE);
        gameResultTextView.setText("You Win!");
        buttonContainer.setVisibility(View.INVISIBLE);
        newGameButton.setEnabled(true);
    }

    @Override
    public void onPlayerLoss() {
        handler.removeCallbacksAndMessages(null);
        gameResultTextView.setVisibility(View.VISIBLE);
        gameResultTextView.setText("You Lose!");
        buttonContainer.setVisibility(View.INVISIBLE);
        newGameButton.setEnabled(true);
    }

    @Override
    public void onCardDealtToPlayer(Card card) {
        playerScoreTextView.setText(gameController.getPlayerScore());
        ImageView cardView =
                (ImageView) LayoutInflater.from(this).inflate(R.layout.poker_card, playerHandLayout, false);
        cardView.setImageResource(card.imageId);
        if (playerHandLayout.getChildCount() > 0) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.overlap);
        }
        playerHandLayout.addView(cardView);
    }

    @Override
    public void onCardDealtToDealer(Card card) {
        ImageView cardView =
                (ImageView) LayoutInflater.from(this).inflate(R.layout.poker_card, dealerHandLayout, false);
        cardView.setImageResource(card.imageId);
        if (playerHandLayout.getChildCount() > 0) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.overlap);
        }
        dealerHandLayout.addView(cardView);
        dealerScoreTextView.setText(gameController.getDealerScore());
        if (!gameController.isYourTurn()) {
            makeDealerMove();
        }
    }
}
