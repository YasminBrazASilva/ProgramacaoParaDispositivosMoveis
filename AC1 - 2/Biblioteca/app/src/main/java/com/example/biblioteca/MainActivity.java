package com.example.biblioteca;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtTitulo, edtAutor;
    Button btnSalvar;
    ListView ltvLivros;
    CheckBox ckbSim, ckbNao;
    Spinner spnCategoria;
    BancoHelper databaseHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaLivros;
    ArrayList<Integer> listaIds;
    String tituloAtual, autorAtual, eConcluidoAtual, categoriaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            edtTitulo = findViewById(R.id.edtTitulo);
            edtAutor = findViewById(R.id.edtAutor);
            ckbSim = findViewById(R.id.ckbSim);
            ckbNao = findViewById(R.id.ckbNao);
            spnCategoria = findViewById(R.id.spnCategoria);
            btnSalvar = findViewById(R.id.btnSalvar);
            ltvLivros = findViewById(R.id.ltvLivros);
            databaseHelper = new BancoHelper(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btnSalvar.setOnClickListener(v -> {
            armazenarDadosDoFormulario();
            if (!tituloAtual.isEmpty() || !autorAtual.isEmpty() || categoriaAtual.isEmpty()) {
                long resultado = databaseHelper.inserirLivro(tituloAtual, autorAtual, eConcluidoAtual, categoriaAtual);
                if (resultado != -1) {
                    Toast.makeText(this, "Usuário salvo!", Toast.LENGTH_SHORT).show();
                    edtTitulo.setText("");
                    edtAutor.setText("");
                    ckbSim.setChecked(false);
                    ckbNao.setChecked(false);
                    carregarLivros();
                } else {
                    Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });


        ltvLivros.setOnItemClickListener((parent, view, position, id) -> {
            int livroId = listaIds.get(position);
            String titulo = listaLivros.get(position).split(" - ")[1];
            String autor = listaLivros.get(position).split(" - ")[2];
            String eConcluido = listaLivros.get(position).split(" - ")[3];
            String categoria = listaLivros.get(position).split(" - ")[4];

            edtTitulo.setText(titulo);
            edtAutor.setText(autor);

            if (eConcluido.equals("Sim")) {
                ckbSim.setChecked(true);
                ckbNao.setChecked(false);
            } else {
                ckbSim.setChecked(false);
                ckbNao.setChecked(true);
            }

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnCategoria.getAdapter();
            int posicaoCategoria = adapter.getPosition(categoria);
            if (posicaoCategoria >= 0) {
                spnCategoria.setSelection(posicaoCategoria);
            }

            btnSalvar.setOnClickListener(v -> {
                String novoTitulo = edtTitulo.getText().toString();
                String novoAutor = edtAutor.getText().toString();
                String novoEConcluido = "Não";
                String novoCategoria = spnCategoria.getSelectedItem().toString();

                if (ckbSim.isChecked()) {
                    eConcluidoAtual = "Sim";
                }

                if (!novoTitulo.isEmpty() || !novoAutor.isEmpty() || !novoCategoria.isEmpty()) {
                    int resultado = databaseHelper.atualizarLivro(livroId, novoTitulo, novoAutor, novoEConcluido, novoCategoria);
                    if (resultado > 0) {
                        Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
                        carregarLivros();
                        edtTitulo.setText("");
                        edtAutor.setText("");
                        ckbSim.setChecked(false);
                        ckbNao.setChecked(false);
                        btnSalvar.setText("Salvar");
                    } else {
                        Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        ltvLivros.setOnItemLongClickListener((adapterView, view1, pos, l) -> {
            try {
                int idLivro = listaIds.get(pos);
                int deletado = databaseHelper.excluirLivro(idLivro);
                if (deletado > 0) {
                    Toast.makeText(this, "Livro excluído!", Toast.LENGTH_SHORT).show();
                    carregarLivros();
                    return true;
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private void armazenarDadosDoFormulario() {
        tituloAtual = edtTitulo.getText().toString();
        autorAtual = edtAutor.getText().toString();
        eConcluidoAtual = "Não";
        categoriaAtual = spnCategoria.getSelectedItem().toString();

        if (ckbSim.isChecked()) {
            eConcluidoAtual = "Sim";
        }
    };

    private void carregarLivros() {
        Cursor cursor = databaseHelper.listarLivros();
        listaLivros = new ArrayList<>();
        listaIds = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String autor = cursor.getString(2);
                String eConcluido = cursor.getString(3);
                String categoria = cursor.getString(4);
                listaLivros.add(id + " - " + titulo + " - " + autor + " - " + eConcluido + " - " + categoria);
                listaIds.add(id);
            } while (cursor.moveToNext());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaLivros);
        ltvLivros.setAdapter(adapter);
    }
}