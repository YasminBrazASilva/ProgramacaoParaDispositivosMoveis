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

public class D_CadastroLoja extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.d_activity_cadastro_loja);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.d_activity_cadastro_loja), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getStringByEditTextId(@IdRes int editTextId) {
        EditText editText = findViewById(editTextId);
        return editText.getText().toString().trim();
    }

    public void cadastrar(View view) {
        String nome = getStringByEditTextId(R.id.Nome);
        String idade = getStringByEditTextId(R.id.Idade);
        String uf = getStringByEditTextId(R.id.UF);
        String cidade = getStringByEditTextId(R.id.Cidade);
        String telefone = getStringByEditTextId(R.id.Telefone);
        String email = getStringByEditTextId(R.id.Email);

        Boolean camposPreenchidos = (
                !(nome.isEmpty())
                && !(idade.isEmpty())
                && !(uf.isEmpty())
                && !(cidade.isEmpty())
                && !(telefone.isEmpty())
                && !(email.isEmpty())
        );

        if(camposPreenchidos) {
            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Preencha os dados cadastrais.", Toast.LENGTH_SHORT).show();
        }
    }
}