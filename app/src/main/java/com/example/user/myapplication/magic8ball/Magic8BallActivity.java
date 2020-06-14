package com.example.user.myapplication.magic8ball;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.myapplication.R;

public class Magic8BallActivity extends AppCompatActivity {

    private static final String[] ANSWERS = {"yes", "no", "maybe", "si"};

    private TextView youAsked;
    private TextView answer;
    private EditText input;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magic8ball_activity);
        getSupportActionBar().setTitle("Welcome to Magic 8 Ball!");
        youAsked = findViewById(R.id.youasked);
        answer = findViewById(R.id.answer);
        input = findViewById(R.id.input);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> answerQuestion());
    }

    private void answerQuestion() {
        youAsked.setText("You asked: " + input.getText().toString());
        String reply = ANSWERS[(int)(Math.random() * ANSWERS.length) ];
        answer.setText(reply);
        input.setText("");
    }
}
