package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText minEditText;
    private EditText maxEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minEditText = findViewById(R.id.minEditText);
        maxEditText = findViewById(R.id.maxEditText);
    }

    public void onClick(View v) {
        if (TextUtils.isEmpty(minEditText.getText().toString()) || TextUtils.isEmpty(maxEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Введите числа", Toast.LENGTH_SHORT).show();
        }
        else {
            int min = Integer.parseInt(minEditText.getText().toString());
            int max = Integer.parseInt(maxEditText.getText().toString());

            // передача в другую активность
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("min", min);
            intent.putExtra("max", max);
            startActivity(intent);
        }
    }

}