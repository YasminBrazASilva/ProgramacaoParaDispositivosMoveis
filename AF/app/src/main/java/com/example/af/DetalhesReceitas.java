package com.example.af;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class DetalhesReceitas extends AppCompatActivity {

    private TextView txtTitulo, txtIngredientes, txtPassos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_receitas);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtIngredientes = findViewById(R.id.txtIngredientes);
        txtPassos = findViewById(R.id.txtPassos);

        String nome = getIntent().getStringExtra("nome");
        List<String> ingredientes = getIntent().getStringArrayListExtra("ingredientes");
        List<String> passos = getIntent().getStringArrayListExtra("passos");

        txtTitulo.setText(nome);
        txtIngredientes.setText(listaParaTexto(ingredientes));
        txtPassos.setText(listaParaTexto(passos));
    }

    private String listaParaTexto(List<String> lista) {
        if (lista == null || lista.isEmpty()) return "Nenhum item.";
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            sb.append("• ").append(item).append("\n");
        }
        return sb.toString();
    }

    public void voltarPagina() {
        Intent intent = new Intent(DetalhesReceitas.this, PaginaInicial.class);
        startActivity(intent);
    }
}