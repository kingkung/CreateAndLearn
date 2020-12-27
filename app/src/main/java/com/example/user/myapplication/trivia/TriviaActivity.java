package com.example.user.myapplication.trivia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.myapplication.R;

public class TriviaActivity extends AppCompatActivity {

    private TextView question;
    private EditText input;
    private TextView answer;
    private Button button;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity);
        getSupportActionBar().setTitle("Trivia!");
        question = findViewById(R.id.question);
        input = findViewById(R.id.input);
        answer = findViewById(R.id.answer);
        button = findViewById(R.id.button);
        runQuestion1();
    }

    public void runQuestion1()
    {
        askQuestion("\nHow many bits are in a byte? ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer("8");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        runQuestion2();
                    }
                });
            }
        });
    }

    public void runQuestion2()
    {
        askQuestion("\nWhich person is considered the 'Father of Java?'"
            + "\n1. Dennis Ritchie"
            + "\n2. Brian Kernighan"
            + "\n3. Bjarne Stroustrup"
            + "\n4. James Gosling");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer("4");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        runQuestion3();
                    }
                });
            }
        });
    }

    public void runQuestion3()
    {
        askQuestion("\nA modulator-demodulator is a hardware device more commonly known as a ________ ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer("modem");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        runQuestion4();
                    }
                });
            }
        });
    }

    public void runQuestion4()
    {
        askQuestion("\n1024 Gigabytes is known as a ______");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer("terabyte");
                button.setText("See final score");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        question.setText("Your final score is: ");
                        answer.setText("" + score);
                        button.setText("Restart game");
                        button.setOnClickListener((view) -> runQuestion1());
                    }
                });
            }
        });
    }

    private void askQuestion(String nextQuestion) {
        question.setText(nextQuestion);
        button.setText("Submit");
        input.setVisibility(View.VISIBLE);
        input.setText("");
        answer.setVisibility(View.INVISIBLE);
    }

    private void showAnswer(String correctAnswer) {
        input.setVisibility(View.GONE);
        answer.setVisibility(View.VISIBLE);
        if (input.getText().toString().toLowerCase()
                .equals(correctAnswer.toLowerCase())) {
            answer.setText("Correct!");
            score++;
        } else {
            answer.setText("Sorry, the answer is " + correctAnswer);
        }
        button.setText("Next Question");
    }
}
