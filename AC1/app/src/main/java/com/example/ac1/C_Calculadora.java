package com.example.ac1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class C_Calculadora extends AppCompatActivity {

    private Double resultado;
    private String primeiroNumeroText, segundoNumeroText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.c_activity_calculadora);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.c_activity_calculadora), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getStringByEditTextId(@IdRes int editTextId) {
        EditText editText = findViewById(editTextId);
        return editText.getText().toString().trim();
    }
    private Double toDouble(String string) {
        return Double.parseDouble(string);
    }

    private boolean camposPreenchidos() {
        primeiroNumeroText = getStringByEditTextId(R.id.primeiroNumero);
        segundoNumeroText = getStringByEditTextId(R.id.segundoNumero);

        if (primeiroNumeroText.isEmpty() || segundoNumeroText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void exibirResultado() {
        Toast.makeText(getApplicationContext(), "Resultado :" + resultado, Toast.LENGTH_SHORT).show();
    };


    public void somar(View view) {
        if(!camposPreenchidos()) return;
        resultado = toDouble(primeiroNumeroText) + toDouble(segundoNumeroText);
        exibirResultado();
    }
    public void subtrair(View view) {
        if(!camposPreenchidos()) return;
        resultado = toDouble(primeiroNumeroText) - toDouble(segundoNumeroText);
        exibirResultado();
    }
    public void multiplicar(View view) {
        if(!camposPreenchidos()) return;
        resultado = toDouble(primeiroNumeroText) * toDouble(segundoNumeroText);
        exibirResultado();
    }
    public void dividir(View view) {
        if(!camposPreenchidos()) return;
        resultado = toDouble(primeiroNumeroText) / toDouble(segundoNumeroText);
        exibirResultado();
    }
}