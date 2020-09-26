package com.example.user.myapplication.colormatchgame;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ColorMatchGameActivity extends AppCompatActivity {

    private static final ImmutableList<Integer> COLORS = ImmutableList.of(
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.rgb(255, 165, 0),
            Color.parseColor("purple")
    );
    private static final int NO_SELECTION = -1;
    private static final int STARTING_INTERVAL = 500;
    private static final int MATCHES_PER_INTERVAL = 10;

    private Handler handler = new Handler(Looper.getMainLooper());
    private GridLayout gridLayout;
    private TextView currentScoreTextView;
    private TextView highScoreTextView;
    private View gameOver;
    private View newGame;
    private Map<Integer, Integer> availableColors = new HashMap<>();
    private List<Integer> availableSquares = new ArrayList<>();
    private Map<Integer, Integer> filledSquares = new HashMap<>();

    private int currentScore;
    private int highScore;
    private int previouslySelectedIndex;
    private Drawable selectedSquareMarker;
    private int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.color_match_game_activity);
        currentScoreTextView = findViewById(R.id.current_score);
        highScoreTextView = findViewById(R.id.high_score);
        gridLayout = findViewById(R.id.grid);
        gameOver = findViewById(R.id.game_over);
        selectedSquareMarker = getDrawable(R.drawable.selected_square);
        newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener((view) -> startNewGame());
    }

    private void startNewGame() {
        interval = STARTING_INTERVAL;
        gameOver.setVisibility(View.INVISIBLE);
        newGame.setEnabled(false);
        if (currentScore > highScore) {
            highScore = currentScore;
        }
        highScoreTextView.setText(
                getResources().getString(R.string.color_match_high_score, highScore));
        currentScore = 0;
        currentScoreTextView.setText(
                getResources().getString(R.string.color_match_score, currentScore));
        filledSquares.clear();
        previouslySelectedIndex = NO_SELECTION;
        availableColors.clear();
        for (int color : COLORS) {
            availableColors.put(color, 6);
        }
        availableSquares.clear();
        gridLayout.setEnabled(true);
        gridLayout.removeAllViews();
        int numTiles = gridLayout.getRowCount() * gridLayout.getColumnCount();
        for (int i = 0; i < numTiles; i++) {
            final int index = i;
            availableSquares.add(index);
            ImageView view = (ImageView) getLayoutInflater().inflate(
                    R.layout.color_tile, gridLayout, false);
            view.setOnClickListener((v) -> selectSquare(view, index));
            gridLayout.addView(view);
        }
        handler.postDelayed(() -> fillRandomSquare(), interval);
    }

    private void fillRandomSquare() {
        if (availableSquares.isEmpty()) {
            gameOver.setVisibility(View.VISIBLE);
            newGame.setEnabled(true);
            return;
        }
        List<Integer> colors = new ArrayList<>(availableColors.keySet());
        int randomColorIndex = ThreadLocalRandom.current().nextInt(0, colors.size());
        int randomColor = colors.get(randomColorIndex);
        int randomSquareIndex =
                ThreadLocalRandom.current().nextInt(0, availableSquares.size());
        int randomSquare = availableSquares.get(randomSquareIndex);
        gridLayout.getChildAt(randomSquare).setBackgroundColor(randomColor);
        decrementAvailableColorCount(randomColor);
        availableSquares.remove(randomSquareIndex);
        filledSquares.put(randomSquare, randomColor);
        handler.postDelayed(() -> fillRandomSquare(), interval);
    }

    private void selectSquare(ImageView view, int index) {
        if (gameOver.getVisibility() == View.VISIBLE
                || !filledSquares.containsKey(index)
                || previouslySelectedIndex == index) {
            return;
        }
        if (previouslySelectedIndex == NO_SELECTION) {
            previouslySelectedIndex = index;
            view.setImageDrawable(selectedSquareMarker);
            return;
        }
        ImageView previouslySelectedView =
                (ImageView) gridLayout.getChildAt(previouslySelectedIndex);
        if (filledSquares.get(previouslySelectedIndex).equals(filledSquares.get(index))) {
            currentScore++;
            currentScoreTextView.setText(
                    getResources().getString(R.string.color_match_score, currentScore));
            view.setBackground(null);
            previouslySelectedView.setBackground(null);
            int prevColor = filledSquares.remove(previouslySelectedIndex);
            int selectedColor = filledSquares.remove(index);
            availableSquares.add(previouslySelectedIndex);
            availableSquares.add(index);
            incrementAvailableColorCount(prevColor);
            incrementAvailableColorCount(selectedColor);
            if (currentScore % MATCHES_PER_INTERVAL == 0) {
                interval = interval * 9 / 10;
            }
        }
        previouslySelectedView.setImageDrawable(null);
        previouslySelectedIndex = NO_SELECTION;
    }

    private void incrementAvailableColorCount(int color) {
        int currentColorCount =
                availableColors.containsKey(color) ? availableColors.get(color) : 0;
        availableColors.put(color, currentColorCount + 1);
    }

    private void decrementAvailableColorCount(int color) {
        if (!availableColors.containsKey(color)) {
            return;
        }
        int currentColorCount = availableColors.get(color);
        if (currentColorCount <= 1) {
            availableColors.remove(color);
            return;
        }
        availableColors.put(color, currentColorCount - 1);
    }
}
