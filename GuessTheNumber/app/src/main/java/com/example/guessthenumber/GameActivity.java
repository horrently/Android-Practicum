package com.example.guessthenumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView guessTextView;
    private int min;
    private int max;
    private int guess;
    private Button LowerButton;
    private Button HigherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessTextView = findViewById(R.id.guessTextView);
        LowerButton = findViewById(R.id.LowerButton);
        HigherButton = findViewById(R.id.HigherButton);

        Intent intent = getIntent();
        min = intent.getIntExtra("min", 0);
        max = intent.getIntExtra("max", 100);

        guessNumber();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LowerButton:
                max = guess - 1;
                guessNumber();
                break;
            case R.id.HigherButton:
                min = guess + 1;
                guessNumber();
                break;
            case R.id.RightAns:
                guessTextView.setText("Ваше число: " + guess);
                LowerButton.setEnabled(false);
                HigherButton.setEnabled(false);
                break;
        }
    }

    private void guessNumber() {
        guess = (min + max) / 2;
        guessTextView.setText("Ваше число: " + guess + "?");
    }

}