package com.example.colortiles;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private View[][] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // массива плиток
        tiles = new View[][]{
                {findViewById(R.id.t00), findViewById(R.id.t01), findViewById(R.id.t02), findViewById(R.id.t03)},
                {findViewById(R.id.t10), findViewById(R.id.t11), findViewById(R.id.t12), findViewById(R.id.t13)},
                {findViewById(R.id.t20), findViewById(R.id.t21), findViewById(R.id.t22), findViewById(R.id.t23)},
                {findViewById(R.id.t30), findViewById(R.id.t31), findViewById(R.id.t32), findViewById(R.id.t33)}
        };

        // случайный цвет для плиток
        Random rand = new Random(System.currentTimeMillis());
        for (View[] row : tiles) {
            for (View tile : row) {
                if (rand.nextDouble() > 0.5) { // шанс 50% на смену цвета
                    changeColor(tile); // меняем цвет плитки
                }
            }
        }
    }

    // координаты из тэга плитки
    private Coord getCoordFromString(String s) {
        return new Coord(s.charAt(0) - '0', s.charAt(1) - '0');
    }

    // изменение цвета плитки
    private void changeColor(View view) {
        int brightColor = getResources().getColor(R.color.bright); // Светлый
        int darkColor = getResources().getColor(R.color.dark); // Темный
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        if (drawable.getColor() == brightColor) { // если текущий цвет светлый, меняем на темный
            view.setBackgroundColor(darkColor);
        }
        else { // иначе меняем на светлый
            view.setBackgroundColor(brightColor);
        }
    }

    // нажатие на плитку
    public void onClick(View v) {
        Coord coord = getCoordFromString(v.getTag().toString()); // получаем координаты плитки
        changeColor(v); // меняем цвет нажатой плитки
        // меняем цвет плиток в той же строке и столбце, что и нажатая
        for (int i = 0; i < 4; i++) {
            changeColor(tiles[coord.x][i]);
            changeColor(tiles[i][coord.y]);
        }
        checkVictory();
    }

    // проверка победы
    private void checkVictory() {
        int darkTilesCount = 0; // счетчик темных плиток
        // перебор плиток, считается кол-во темных
        for (View[] row : tiles) {
            for (View item : row) {
                ColorDrawable background = (ColorDrawable) item.getBackground();
                int backgroundColor = background != null ? background.getColor() : 0;
                int targetColor = getResources().getColor(R.color.dark);
                if (backgroundColor == targetColor) {
                    darkTilesCount++;
                }
            }
        }
        // все темные или светлые, выводим сообщение о победе
        if (darkTilesCount == 0 || darkTilesCount == 16) {
            Toast.makeText(this, "Вы победили!", Toast.LENGTH_LONG).show();
        }
    }
}
