package com.example.ac1;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class E_CheckboxLetras extends AppCompatActivity {
    private LinearLayout checkBoxContainer;
    private List<CheckBox> checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.e_activity_checkbox_letras);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkBoxContainer = findViewById(R.id.checkBoxContainer);
    }

    private String getStringByEditTextId(@IdRes int editTextId) {
        EditText editText = findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    private char[] getLettersFromString(String string) {
        char[] letras = new char[string.length()];

        for (int i = 0; i < string.length(); i++) {
            letras[i] = string.charAt(i);
        }

        return letras;
    }

    public void criarCheckboxes (View view) {
        String nomeCheckbox = getStringByEditTextId(R.id.nomeCheckbox);
        char[] letrasDoNome = getLettersFromString(nomeCheckbox);

        checkBoxList = new ArrayList<>();
        checkBoxContainer.removeAllViews();

        for (char letra : letrasDoNome) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(String.valueOf(letra));
            checkBoxContainer.addView(checkBox);
            checkBoxList.add(checkBox);
        }
    }
}