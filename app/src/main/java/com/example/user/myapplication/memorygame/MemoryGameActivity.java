package com.example.user.myapplication.memorygame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
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
    // No card has been selected yet
    private static final int NO_CARD_SELECTED = -1;
    // The amount of time we show an incorrect match before showing the backs of the cards
    private static final long DELAY_AFTER_INCORRECT_MATCH_IN_MILLIS = 2500L;

    private Handler handler = new Handler(Looper.getMainLooper());

    // Views
    private GridLayout gridLayout;
    private View youWin;
    private View newGame;

    // The array of cards - 2 of each
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
    // The deck of cards
    private List<Integer> deck;
    // The set of cards which have been revealed (because we found their matches)
    private Set<Integer> revealed = new HashSet<>();
    // Set this to true when a match is found
    private boolean disableClicks;
    // The index of the first selected card.
    private int firstSelectedCard = NO_CARD_SELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Flat for the "no title" feature, turning off the title at the top of the screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.memory_game_activity);
        // Set components using findViewById
        gridLayout = findViewById(R.id.grid);
        youWin = findViewById(R.id.you_win);
        newGame = findViewById(R.id.new_game);
        // Set the listener for the 'New Game' button
        newGame.setOnClickListener((view) -> startNewGame());
        // Number of rows: 4, number of columns: 4, number of cards = 4 * 4 = 16
        int numCards = gridLayout.getColumnCount() * gridLayout.getRowCount();
        // Add a view to the gridLayout for each card on the screen.
        for (int i = 0; i < numCards; i++) {
            final int index = i;
            // LayoutInflater - Converts a layout XML file into View objects
            // inflate() - Inflate a new view hierarchy from the specified xml resource
            ImageView view = (ImageView) getLayoutInflater().inflate(
                    R.layout.memory_card, gridLayout, false);
            // Set the listener for thc card
            view.setOnClickListener((v) -> revealCard(view, index));
            // Add the card to the grid layout
            gridLayout.addView(view);
        }
        // Start the game.
        startNewGame();
    }

    /**
     * Starts a new game
     * Resets deck list, revealed list, grid
     * Shuffles deck
     */
    private void startNewGame() {
        // Turn off visibility for "You Win" text view
        // Disable New Game button
        youWin.setVisibility(View.INVISIBLE);
        newGame.setEnabled(false);
        // Add all of the cards into the deck
        deck = new ArrayList<>();
        for (int card : cards) {
            deck.add(card);
        }
        // Shuffle the deck
        Collections.shuffle(deck);
        // Clear the set of revealed cards.
        revealed.clear();
        firstSelectedCard = NO_CARD_SELECTED;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(R.drawable.card_back);
        }
    }

    private void revealCard(ImageView view, int index) {
        // If a user clicks a card whose match is already found or is already currently turned over
        if (disableClicks || firstSelectedCard == index || revealed.contains(index)) {
            return;
        }
        view.setImageResource(deck.get(index));
        // If this is the first turned card, set prevIndex to index
        if (firstSelectedCard == NO_CARD_SELECTED) {
            firstSelectedCard = index;
            return;
        }
        // If the two cards match
        if (deck.get(firstSelectedCard).equals(deck.get(index))) {
            // add both cards to the revealed list
            revealed.add(index);
            revealed.add(firstSelectedCard);
            // reset prevIndex
            firstSelectedCard = NO_CARD_SELECTED;
            if (revealed.size() == deck.size()) {
                youWin.setVisibility(View.VISIBLE);
                newGame.setEnabled(true);
            }
            return;
        } else {
            // Reveal the incorrect match for a set amount of time, then show the card backs again
            disableClicks = true;
            handler.postDelayed((Runnable) () -> {
                ((ImageView) gridLayout.getChildAt(firstSelectedCard))
                        .setImageResource(R.drawable.card_back);
                ((ImageView) gridLayout.getChildAt(index))
                        .setImageResource(R.drawable.card_back);
                firstSelectedCard = NO_CARD_SELECTED;
                disableClicks = false;
            }, DELAY_AFTER_INCORRECT_MATCH_IN_MILLIS);
        }
    }
}
