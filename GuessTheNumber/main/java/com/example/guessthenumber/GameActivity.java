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
    private Button noButton;
    private Button yesButton;

    private boolean isGuessingLower;
    private int noCount; // счетчик ответов "Нет"
    private int previousGuess; // предыдущее предполагаемое число

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessTextView = findViewById(R.id.guessTextView);
        noButton = findViewById(R.id.NoButton);
        yesButton = findViewById(R.id.YesButton);

        Intent intent = getIntent();
        min = intent.getIntExtra("min", 0);
        max = intent.getIntExtra("max", 100);

        isGuessingLower = true;
        noCount = 0;
        previousGuess = -1; // предыдущее предполагаемое число

        guessNumber();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.YesButton:
                if (isGuessingLower) {
                    max = guess - 1;
                } else {
                    min = guess + 1;
                }
                break;
            case R.id.NoButton:
                // является ли текущее число таким же, как и предыдущее
                if (guess == previousGuess) {
                    noCount++; // увеличиваем счетчик ответов "Нет"
                    if (noCount == 2) {
                        // Если ответили "Нет" на оба вопроса, то это загаданное число
                        guessTextView.setText("Ваше число: " + guess);
                        yesButton.setVisibility(View.GONE);
                        noButton.setVisibility(View.GONE);
                        return;
                    }
                } else {
                    // если текущее число отличается от предыдущего, сбрасываем счетчик
                    noCount = 1;
                }
                previousGuess = guess; // обновляем предыдущее предполагаемое число
                break;
        }
        // если еще не ответили "Нет" на оба вопроса,
        // переходим к следующему предполагаемому числу и задаем те же два вопроса
        isGuessingLower = !isGuessingLower;
        guessNumber();
    }

    private void guessNumber() {
        guess = (min + max) / 2;
        if (isGuessingLower) {
            guessTextView.setText("Ваше число меньше " + guess + "?");
        } else {
            guessTextView.setText("Ваше число больше " + guess + "?");
        }
    }
}
