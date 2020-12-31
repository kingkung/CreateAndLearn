package com.example.user.myapplication.huntthewumpus;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user.myapplication.R;

public class HuntTheWumpusActivity extends AppCompatActivity {

    CaveMaze caveMaze;
    View caveBackground;
    View caveBackground2;
    View wumpus;
    View bottomlessPit;
    TextView grenadeCount;
    TextView wumpusCount;
    TextView resultText;
    Switch tossGrenadeSwitch;
    View newGameButton;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Hunt the Wumpus!");
        setContentView(R.layout.activity_hunt_the_wumpus);
        caveMaze = new CaveMaze();
        caveBackground = findViewById(R.id.cave_background);
        caveBackground2 = findViewById(R.id.cave_background2);
        wumpus = findViewById(R.id.wumpus);
        bottomlessPit = findViewById(R.id.bottomless_pit);
        tossGrenadeSwitch = findViewById(R.id.toss_grenade_switch);
        grenadeCount = findViewById(R.id.grenade_count);
        wumpusCount = findViewById(R.id.wumpus_count);
        resultText = findViewById(R.id.result);
        newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
        initializeDirectionalButton(R.id.button_left, Action.WEST);
        initializeDirectionalButton(R.id.button_right, Action.EAST);
        initializeDirectionalButton(R.id.button_up, Action.NORTH);
        initializeDirectionalButton(R.id.button_down, Action.SOUTH);
        startNewGame();
    }

    private void initializeDirectionalButton(int buttonId, Action action) {
        View button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null) {
                    toast.cancel();
                }
                if (tossGrenadeSwitch.isChecked()) {
                    ObjectAnimator animator = ObjectAnimator
                            .ofFloat(caveBackground, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                            .setDuration(700);
                    animator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    String result = caveMaze.tossGrenade(action.throwCommand);
                                    resultText.setText(result);
                                    updateGrenadeAndWumpusCount();
                                    if (!caveMaze.stillWumpi()) {
                                        showGameEndDialog("You have successfully defeated the wumpi! Congratulations!");
                                    } else if (caveMaze.getGrenades() <= 0) {
                                        showGameEndDialog("You're out of grenades! Please try again.");
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                    animator.start();
                    return;
                }
                caveBackground.startAnimation(AnimationUtils.loadAnimation(HuntTheWumpusActivity.this, action.inAnimationId));
                caveBackground2.startAnimation(AnimationUtils.loadAnimation(HuntTheWumpusActivity.this, action.outAnimationId));
                caveBackground.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        String result = caveMaze.move(action.moveCommand);
                        resultText.setText(result);
                        if (result.toLowerCase().contains("wumpus")) {
                            wumpus.setVisibility(View.VISIBLE);
                        } else if (result.toLowerCase().contains("pit")) {
                            bottomlessPit.setVisibility(View.VISIBLE);
                        } else {
                            showHintsIfTheyExist();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    private void showGameEndDialog(String endGameMessage) {
        new AlertDialog.Builder(HuntTheWumpusActivity.this)
                .setCancelable(false)
                .setMessage(endGameMessage)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startNewGame();
                    }
                }).show();
    }

    private void startNewGame() {
        caveMaze = new CaveMaze();
        tossGrenadeSwitch.setChecked(false);
        wumpus.setVisibility(View.INVISIBLE);
        bottomlessPit.setVisibility(View.INVISIBLE);
        resultText.setText("");
        updateGrenadeAndWumpusCount();
        showHintsIfTheyExist();
    }

    private void updateGrenadeAndWumpusCount() {
        wumpusCount.setText("Wumpi left: " + caveMaze.getNumWumpi());
        grenadeCount.setText("Grenades left: " + caveMaze.getGrenades());
    }

    private void showHintsIfTheyExist() {
        if (!TextUtils.isEmpty(caveMaze.getHints())) {
            toast = Toast.makeText(HuntTheWumpusActivity.this, caveMaze.getHints(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}