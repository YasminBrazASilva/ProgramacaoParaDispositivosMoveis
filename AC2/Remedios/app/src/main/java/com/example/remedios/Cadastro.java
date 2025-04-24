package com.example.remedios;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Cadastro extends AppCompatActivity {

    EditText edtNome, edtDescricao, edtHorario, edtConsumido;
    Button btnCadastrar;
    BancoHelper databaseHelper;
    int remedioId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNome = findViewById(R.id.editTextNome);
        edtDescricao = findViewById(R.id.editTextDescricao);
        edtHorario = findViewById(R.id.editTextHorario);
        edtConsumido = findViewById(R.id.editTextConsumido);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        databaseHelper = new BancoHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            obterDadosExtra(intent);
            btnCadastrar.setText("Atualizar");
        }

        btnCadastrar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String descricao = edtDescricao.getText().toString();
            String horario = edtHorario.getText().toString();
            String consumido = edtConsumido.getText().toString();

            if (!nome.isEmpty() && !descricao.isEmpty() && !horario.isEmpty() && !consumido.isEmpty()) {
                if (remedioId == -1) {
                    // Cadastro
                    long resultado = databaseHelper.inserirRemedio(nome, descricao, horario, consumido);
                    if (resultado != -1) {
                        exibirToast("Remédio cadastrado!");
                        carregarTelaPrincipal();
                    } else {
                        exibirToast("Erro ao cadastrar!");
                    }

                } else {
                    // Atualização
                    int resultado = databaseHelper.atualizarRemedio(remedioId, nome, descricao, horario, consumido);
                    if (resultado > 0) {
                        exibirToast("Remédio atualizado!");
                        carregarTelaPrincipal();
                    } else {
                        exibirToast("Erro ao atualizar!");
                    }
                }
            } else {
                exibirToast("Preencha todos os campos!");
            }
        });
    }

    private void obterDadosExtra(Intent intent) {
        remedioId = intent.getIntExtra("id", -1);
        edtNome.setText(intent.getStringExtra("nome"));
        edtDescricao.setText(intent.getStringExtra("descricao"));
        edtHorario.setText(intent.getStringExtra("horario"));
        edtConsumido.setText(intent.getStringExtra("consumido"));
    }

    private void exibirToast(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void limparCampos() {
        edtNome.setText("");
        edtDescricao.setText("");
        edtHorario.setText("");
    }

    private void carregarTelaPrincipal() {
        Intent intent = new Intent(Cadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}