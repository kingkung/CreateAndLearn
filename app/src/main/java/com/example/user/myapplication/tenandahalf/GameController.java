package com.example.user.myapplication.tenandahalf;

import com.example.user.myapplication.R;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private static final double MAX_SCORE_BEFORE_BUST = 10.5;
    private static final int NUM_CARDS_IN_DRAGON = 5;
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

    private final List<Card> deck = new ArrayList<>();
    private final GameEventCallback callback;

    private int playerCardCount;
    private int dealerCardCount;
    private double playerScore;
    private double dealerScore;
    private boolean isYourTurn;

    /**
     * Interface to call back to the UI when certain events happen within the game.
     */
    public interface GameEventCallback {
        /** Called when a card has been dealt to the player. */
        void onCardDealtToPlayer(Card card);
        /** Card when a player has been dealt to the dealer. */
        void onCardDealtToDealer(Card card);
        /** Called when the player has won the game. */
        void onPlayerWin();
        /** Called when the player has lost the game. */
        void onPlayerLoss();
    }

    public GameController(GameEventCallback callback) {
        this.callback = callback;
    }

    /**
     * Resets the game state to start a new game.
     *
     * <p>Sets your and dealer's score and card count back to zero, and reshuffles the deck.
     */
    public void resetGameState() {
        isYourTurn = true;
        playerScore = 0;
        dealerScore = 0;
        playerCardCount = 0;
        dealerCardCount = 0;
        deck.clear();
        deck.addAll(DECK);
        Collections.shuffle(deck);
    }

    /** Returns your score in a string format. */
    public String getPlayerScore() {
        return getScoreText(playerScore);
    }

    /** Returns dealer score in a string format. */
    public String getDealerScore() {
        return getScoreText(dealerScore);
    }

    /** Ends your turn and begins the dealer's turn. */
    public void endYourTurn() {
        this.isYourTurn = false;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    /** Removes a card from the deck, adds it to your score, and calls back to the UI. */
    public void dealCardToYou() {
        Card card = deck.remove(0);
        playerScore += card.value;
        playerCardCount++;
        callback.onCardDealtToPlayer(card);
        checkIfGameHasEnded();
    }

    /** Removes a card from the deck, adds it to the dealer's score, and calls back to the UI. */
    public void dealCardToDealer() {
        Card card = deck.remove(0);
        dealerScore += card.value;
        dealerCardCount++;
        callback.onCardDealtToDealer(card);
        checkIfGameHasEnded();
    }

    /**
     * Checks if the game has ended, and triggers the correct {@link GameEventCallback} event if so.
     */
    public boolean checkIfGameHasEnded() {
        if (isYourTurn) {
            if (playerScore > MAX_SCORE_BEFORE_BUST) {
                callback.onPlayerLoss();
                return true;
            } else if (playerCardCount == NUM_CARDS_IN_DRAGON) {
                callback.onPlayerWin();
                return true;
            }
            return false;
        } else {
            if (dealerScore > MAX_SCORE_BEFORE_BUST) {
                callback.onPlayerWin();
                return true;
            }  else if (dealerCardCount == NUM_CARDS_IN_DRAGON) {
                callback.onPlayerLoss();
                return true;
            } else if (dealerScore > playerScore) {
                callback.onPlayerLoss();
                return true;
            }
            return false;
        }
    }

    private static String getScoreText(double d) {
        return String.format("%.1f", d);
    }
}
