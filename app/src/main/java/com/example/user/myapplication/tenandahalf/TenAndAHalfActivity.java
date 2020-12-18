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

public class TenAndAHalfActivity extends AppCompatActivity {

    private final long DEALER_MOVE_DELAY_IN_MILLIS = 2500;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private TenDotFiveGame game;

    private LinearLayout playerHandLayout;
    private LinearLayout dealerHandLayout;
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
        hitButton.setOnClickListener((v) -> dealCardToPlayer());
        stayButton = findViewById(R.id.stay);
        stayButton.setOnClickListener((v) -> startDealerTurn());
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
        game = new TenDotFiveGame();
        newGameButton.setEnabled(false);
        gameResultTextView.setVisibility(View.INVISIBLE);
        playerScoreTextView.setText(Double.toString(game.getPlayerTotal()));
        dealerScoreTextView.setText(Double.toString(game.getDealerTotal()));
        playerHandLayout.removeAllViews();
        dealerHandLayout.removeAllViews();
        buttonContainer.setVisibility(View.VISIBLE);
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        // Deal one card to the player and one card to the dealer.
        dealCardToPlayer();
        dealCardToDealer();
    }

    private void startDealerTurn() {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        nextDealerMove();
    }

    private void nextDealerMove() {
        // Check for end game state, end game if dealer busts or shouldn't hit again.
        if (game.dealerIsBusted() || !game.dealerShouldHit()) {
            endGame();
            return;
        }
        // Otherwise, deal the next dealer card on a 2.5s delay, and recursively call this method.
        handler.postDelayed(() -> {
            dealCardToDealer();
            nextDealerMove();
        }, DEALER_MOVE_DELAY_IN_MILLIS);
    }

    private void endGame() {
        handler.removeCallbacksAndMessages(null);
        gameResultTextView.setVisibility(View.VISIBLE);
        gameResultTextView.setText(game.getWinner());
        buttonContainer.setVisibility(View.INVISIBLE);
        newGameButton.setEnabled(true);
    }

    private void dealCardToPlayer() {
        Card card = game.playerHit();
        playerScoreTextView.setText(Double.toString(game.getPlayerTotal()));
        inflateCardViewAndAddToLayout(card, playerHandLayout);
        if (game.playerIsBusted()) {
            endGame();
        }
    }

    private void dealCardToDealer() {
        Card card = game.dealerHit();
        dealerScoreTextView.setText(Double.toString(game.getDealerTotal()));
        inflateCardViewAndAddToLayout(card, dealerHandLayout);
    }

    private void inflateCardViewAndAddToLayout(Card card, LinearLayout layout) {
        // Inflate a view representing a card.
        ImageView cardView =
                (ImageView) LayoutInflater.from(this).inflate(R.layout.poker_card, layout, false);
        // Set the ImageView resource to the card's image id
        cardView.setImageResource(card.getImageResId());
        // If this isn't the first card in the layout, adjust the margins so that this card overlaps
        // the previous card in the layout.
        if (layout.getChildCount() > 0) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.overlap);
        }
        // Add the card to the layout.
        layout.addView(cardView);
    }
}
