package com.example.af;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaginaInicial extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private EditText edtNome, edtEstoque;
    private RecyclerView recyclerReceitas;
    private List<Receita> listaReceitas = new ArrayList<>();
    private ReceitaAdapter adapter;
    private Receita ReceitaEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        carregarReceitas();
    }

    private void carregarReceitas() {
        db.collection("receitas")
                .get()
                .addOnSuccessListener(query -> {
                    listaReceitas.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Receita r = doc.toObject(Receita.class);
                        r.setId(doc.getId());
                        listaReceitas.add(r);
                    }
                    adapter.notifyDataSetChanged();
                });

        adapter.setOnItemClickListener(Receita -> {
            edtNome.setText(Receita.getNome());
            edtEstoque.setText(String.valueOf(Receita.getIngredientes()));
            ReceitaEditando = Receita;
        });

    }
}