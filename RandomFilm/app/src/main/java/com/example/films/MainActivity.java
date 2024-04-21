package com.example.films;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;
    private TextView movieInfoTextView;
    private Button showMovieButton;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieInfoTextView = findViewById(R.id.movieInfo);
        showMovieButton = findViewById(R.id.ButtonNext);
        random = new Random();

        // получение списка фильмов
        try {
            InputStream stream = getAssets().open("movies.json");
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            movies = gson.fromJson(reader, new TypeToken<List<Movie>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        showMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movies != null && !movies.isEmpty()) {
                    // случайный фильм
                    int randomIndex = random.nextInt(movies.size());
                    Movie randomMovie = movies.get(randomIndex);
                    displayMovieInfo(randomMovie);
                    // удаление фильма, чтобы не повторялся
                    movies.remove(randomIndex);
                    if (movies.isEmpty()) {
                        showMovieButton.setEnabled(false);
                        showMovieButton.setText("Фильмы закончились");
                    }
                }
            }
        });
    }

    private void displayMovieInfo(Movie movie) {
        String info = "Название: " + movie.getTitle() + "\n" +
                "Год: " + movie.getYear() + "\n" +
                "Жанр: " + movie.getGenre() + "\n" +
                "Режиссер: " + movie.getDirector() + "\n" +
                "Оценка: " + movie.getRating();
        movieInfoTextView.setText(info);
    }
}