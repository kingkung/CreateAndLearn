package com.example.user.myapplication.caesarcipher;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.myapplication.R;

public class CaesarCipherActivity extends AppCompatActivity {

    private EditText inputWords;
    private EditText inputShift;
    private Button button;
    private TextView encodedOutput;
    private TextView decodedOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caesar_cipher_activity);
        getSupportActionBar().setTitle("Caesar Cipher");
        inputWords = findViewById(R.id.inputWords);
        inputShift = findViewById(R.id.inputShift);
        button = findViewById(R.id.button);
        encodedOutput = findViewById(R.id.encoded);
        decodedOutput = findViewById(R.id.decoded);
        button.setOnClickListener((v) -> showEncodedAndDecodedWords());
    }

    private void showEncodedAndDecodedWords() {
        String words = inputWords.getText().toString();
        int shift = Integer.parseInt(inputShift.getText().toString());
        String encoded = CaesarCipher.encode(words, shift);
        String decoded = CaesarCipher.decode(encoded, shift);
        encodedOutput.setText("Encoded: " + encoded);
        decodedOutput.setText("Decoded: " + decoded);
        inputWords.setText("");
        inputShift.setText("");
    }
}
