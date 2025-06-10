package com.example.af;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    private RecyclerView recyclerReceitas;
    private List<Receita> listaReceitas = new ArrayList<>();
    private ReceitaAdapter adapter;

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

        recyclerReceitas = findViewById(R.id.recyclerReceitas);
        recyclerReceitas.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReceitaAdapter(listaReceitas);
        recyclerReceitas.setAdapter(adapter);

        adapter.setOnItemClickListener(receita -> {
            Intent intent = new Intent(PaginaInicial.this, DetalhesReceitas.class);

            intent.putExtra("nome", receita.getNome());
            intent.putExtra("ingredientes", new ArrayList<>(receita.getIngredientes()));
            intent.putExtra("passos", new ArrayList<>(receita.getPassos()));

            startActivity(intent);
        });

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
                        Log.d("Firestore", "Receita carregada: " + r.getNome());
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}