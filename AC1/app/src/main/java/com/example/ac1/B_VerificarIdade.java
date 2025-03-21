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

public class B_VerificarIdade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.b_activity_verificar_idade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.b_activity_verificar_idade), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private String getStringByEditTextId(@IdRes int editTextId) {
        EditText editText = findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    public void verificarIdade(View view) {
        String nome = getStringByEditTextId(R.id.nome);
        String idadeTexto = getStringByEditTextId(R.id.idade);

        if (nome.isEmpty() || idadeTexto.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        };

        int idade = Integer.parseInt(idadeTexto);
        String validacao = idade >= 18 ? "Maior de idade" : "Menor de idade";

        Toast.makeText(getApplicationContext(), validacao, Toast.LENGTH_SHORT).show();
    }
}