package com.example.remedios;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewRemedios;
    ArrayList<String> listaRemedios;
    ArrayAdapter<String> adapter;
    BancoHelper databaseHelper;

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

        // CARREGAR REMÉDIOS NO INÍCIO
        listViewRemedios = findViewById(R.id.ltvRemedios);
        databaseHelper = new BancoHelper(this);
        carregarRemedios();


        // CONFIGURAR ATUALIZAÇÃO
        listViewRemedios.setOnItemClickListener((parent, view, position, id) -> {
            String itemSelecionado = listaRemedios.get(position);
            String[] partes = itemSelecionado.split(" - ");

            Intent intent = new Intent(MainActivity.this, Cadastro.class);
            intent.putExtra("id", Integer.parseInt(partes[0].trim()));
            intent.putExtra("nome", partes[1].trim());
            intent.putExtra("descricao", partes[2].trim());
            intent.putExtra("horario", partes[3].trim());
            intent.putExtra("consumido", partes[4].trim());
            startActivity(intent);
        });

        // CONFIGURAR EXCLUSÃO
        listViewRemedios.setOnItemLongClickListener((parent, view, position, id) -> {
            String itemSelecionado = listaRemedios.get(position);
            String[] partes = itemSelecionado.split(" - ");
            int remedioId = Integer.parseInt(partes[0].trim());

            int resultado = databaseHelper.excluirRemedio(remedioId);
            if (resultado > 0) {
                exibirToast("Remédio excluído.");
                carregarRemedios();
            } else {
                exibirToast("Erro ao excluir.");
            }

            return true;
        });

        // CONFIGURAR NOTIFICAÇÃO
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);

    }

    private void carregarRemedios() {
        Cursor cursor = databaseHelper.listarRemedios();
        listaRemedios = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String descricao = cursor.getString(2);
                String horario = cursor.getString(3);
                String consumido = cursor.getString(4);

                List<String> valores_positivos = Arrays.asList("Sim", "Sí", "Yes");
                String emoji = valores_positivos.contains(consumido.trim()) ? "✅" : "⚠️";

                String remedioFormatado = id + " - " + nome + " - " + descricao + " - " + horario + " - " + consumido + " " + emoji;
                listaRemedios.add(remedioFormatado);

            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRemedios);
        listViewRemedios.setAdapter(adapter);
    }


    public void carregarTelaCadastro(View view) {
        Intent intent = new Intent(MainActivity.this, Cadastro.class);
        startActivity(intent);
    }

    private void exibirToast(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}