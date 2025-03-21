package com.example.ac1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class A_PaginaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_pagina_inicial), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void carregarActivity(Class<?> activityDestino) {
        Intent intent = new Intent(A_PaginaInicial.this, activityDestino);
        startActivity(intent);
    }
    public void carregarVerificarIdade(View view) {
        carregarActivity(B_VerificarIdade.class);
    }
    public void carregarCalculadora(View view) {
        carregarActivity(C_Calculadora.class);
    }
    public void carregarCadastroLoja(View view) {
        carregarActivity(D_CadastroLoja.class);
    }
    public void carregarCheckboxLetras(View view) {
        carregarActivity(E_CheckboxLetras.class);
    }
    public void carregarPreferencias(View view) {
        carregarActivity(F_Preferencias.class);
    }

}

