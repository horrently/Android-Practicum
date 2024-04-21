package com.example.arrayadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<String> names;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private int lastSelectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // список для хранения имен
        names = new ArrayList<>();

        // интерфейс со списком
        lv = findViewById(R.id.listView);

        // адаптер
        adapter = new ArrayAdapter<>(this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                names);
        lv.setAdapter(adapter);

        // обработчик нажатия на элемент списка для выделения фамилии
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // сброс выделения у всех элементов списка
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                // выделение элемента
                view.setBackgroundColor(Color.LTGRAY);
                lastSelectedIndex = position;
            }
        });

        // кнопка и обработчик для добавления новых элементов в список
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRandomPerson(names, adapter);
            }
        });

        // кнопка и обработчик для сортировки
        Button sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(names);
                adapter.notifyDataSetChanged();
            }
        });

    }

    // добавление случайного человека в список
    private void addRandomPerson(List<String> names, ArrayAdapter<String> adapter) {
        // получаем массивы имен и фамилий из ресурсов
        String[] firstNames = getResources().getStringArray(R.array.first_names);
        String[] lastNames = getResources().getStringArray(R.array.last_names);
        // генерируем случайные индексы для выбора имени и фамилии
        Random random = new Random();
        int firstNameIndex = random.nextInt(firstNames.length);
        int lastNameIndex = random.nextInt(lastNames.length);
        // добавление и создание фи
        String fullName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];
        names.add(fullName);
        // уведомление адаптера о изменениях
        adapter.notifyDataSetChanged();
    }


}
