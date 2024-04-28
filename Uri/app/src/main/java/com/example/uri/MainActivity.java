package com.example.uri;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private RadioGroup radioGroup;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        radioGroup = findViewById(R.id.radioGroup);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String text = editText.getText().toString();

                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedText = selectedRadioButton.getText().toString();

                    if (selectedText.equals("Веб-адрес")) {
                        openWebPage(text);
                    }
                    else if (selectedText.equals("Геоточка")) {
                        showOnMap(text);
                    }
                    else if (selectedText.equals("Телефон")) {
                        makePhoneCall(text);
                    }
                }
                else {
                    analyzeText(text);
                }
            }
        });
    }

    // браузер
    private void openWebPage(String url) {
        if (url.matches("^(http://|https://).*")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else {
            showErrorMessage();
        }
    }

    // координаты
    private void showOnMap(String coordinates) {
        if (coordinates.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
            Uri gmmIntentUri = Uri.parse("geo:" + coordinates);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else {
            showErrorMessage();
        }
    }

    // номер телефона
    private void makePhoneCall(String phoneNumber) {
        if (phoneNumber.matches("^(8|\\+7)\\d{10}$")) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
        else {
            showErrorMessage();
        }
    }

    // ничего не выбрано
    private void analyzeText(String text) {
        if (text.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
            showOnMap(text);
        } else if (text.matches("^(8|\\+7)\\d{10}$")) {
            makePhoneCall(text);
        } else if (text.matches("^(http://|https://).*")) {
            openWebPage(text);
        } else {
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Неверный ввод", Toast.LENGTH_SHORT).show();
    }
}