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

    private List<Person> peopleList;
    private ArrayAdapter<Person> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем список для хранения имен
        List<String> names = new ArrayList<>();

        // Создаем интерфейс со списком
        ListView lv = findViewById(R.id.listView);

        // Добавляем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, names);
        lv.setAdapter(adapter);

        // TODO: добавить обработчик нажатия на элемент списка для выделения фамилии
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Сбрасываем выделение у всех элементов списка
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                // Выделяем выбранный элемент
                view.setBackgroundColor(Color.LTGRAY);
            }
        });

        // TODO: добавить кнопку и обработчик для добавления новых элементов в список
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRandomPerson(names, adapter);
            }
        });
    }

    // Метод для добавления случайного человека в список
    private void addRandomPerson(List<String> names, ArrayAdapter<String> adapter) {
        // Получаем массивы имен и фамилий из ресурсов
        String[] firstNames = getResources().getStringArray(R.array.first_names);
        String[] lastNames = getResources().getStringArray(R.array.last_names);
        // Генерируем случайные индексы для выбора имени и фамилии
        Random random = new Random();
        int firstNameIndex = random.nextInt(firstNames.length);
        int lastNameIndex = random.nextInt(lastNames.length);
        // Формируем полное имя и добавляем его в список
        String fullName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];
        names.add(fullName);
        // Уведомляем адаптер о изменениях
        adapter.notifyDataSetChanged();
    }

}
