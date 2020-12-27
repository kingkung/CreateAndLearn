package com.example.user.myapplication.tendotfive;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user.myapplication.R;

public class TenDotFiveActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private TenDotFiveGame game;

    private LinearLayout playerHandLayout;
    private LinearLayout dealerHandLayout;
    private TextView playerScoreTextView;
    private TextView dealerScoreTextView;
    private Button hitButton;
    private Button stayButton;
    private Button newGameButton;
    @Nullable private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ten_and_a_half_activity);
        hitButton = findViewById(R.id.hit);
        hitButton.setOnClickListener((v) -> dealCardToPlayer());
        stayButton = findViewById(R.id.stay);
        stayButton.setOnClickListener((v) -> nextDealerMove());
        playerHandLayout = findViewById(R.id.player_cards);
        playerScoreTextView = findViewById(R.id.player_score);
        dealerHandLayout = findViewById(R.id.dealer_cards);
        dealerScoreTextView = findViewById(R.id.dealer_score);
        newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener((v) -> startNewGame());
    }

    private void startNewGame() {
        game = new TenDotFiveGame();
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        playerHandLayout.removeAllViews();
        dealerHandLayout.removeAllViews();
        // Deal one card to the player and one card to the dealer.
        dealCardToPlayer();
        dealCardToDealer();
    }

    private void nextDealerMove() {
        // Check for end game state, end game if dealer busts or shouldn't hit again.
        if (game.dealerIsBusted() || !game.dealerShouldHit()) {
            endGame();
            return;
        }
        // Otherwise, deal the next dealer card, and recursively call this method.
        dealCardToDealer();
        nextDealerMove();
    }

    private void endGame() {
        handler.removeCallbacksAndMessages(null);
        toast = Toast.makeText(this, game.getWinner(), Toast.LENGTH_LONG);
        toast.show();
    }

    private void dealCardToPlayer() {
        Card card = game.playerHit();
        playerScoreTextView.setText(
                getResources().getString(R.string.player_score, game.getPlayerTotal()));
        inflateCardViewAndAddToLayout(card, playerHandLayout);
        if (game.playerIsBusted()) {
            endGame();
        }
    }

    private void dealCardToDealer() {
        Card card = game.dealerHit();
        dealerScoreTextView.setText(
                getResources().getString(R.string.dealer_score, game.getDealerTotal()));
        inflateCardViewAndAddToLayout(card, dealerHandLayout);
    }

    private void inflateCardViewAndAddToLayout(Card card, LinearLayout layout) {
        // Inflate a view representing a card.
        ImageView cardView =
                (ImageView) LayoutInflater.from(this)
                        .inflate(R.layout.poker_card, layout, false);
        // Set the ImageView resource to the card's image id
        cardView.setImageResource(card.getImageResId());
        // Add the card to the layout.
        layout.addView(cardView);
    }
}
