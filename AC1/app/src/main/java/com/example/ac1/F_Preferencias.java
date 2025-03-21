package com.example.ac1;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class F_Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.f_activity_preferencias);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private Boolean isCheckboxSelectedById(@IdRes int checkboxId) {
        CheckBox checkbox = findViewById(checkboxId);
        return checkbox.isChecked();
    }

    private void exibirEscolhas(List<String> escolhas) {
        String resultado;

        if (!escolhas.isEmpty()) {
            resultado = "Preferências de " + String.join(", ", escolhas);
            resultado = resultado.replaceAll(", ([^,]+)$", " e $1");
            resultado += " salvas.";
        } else {
            resultado = "Nenhuma preferência foi escolhida.";
        }

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
    }

    public void salvarPreferencias(View view) {
        List<String> escolhas = new ArrayList<>();

        if (isCheckboxSelectedById(R.id.checkboxNotificacao)) {
            escolhas.add("notificações");
        }
        if (isCheckboxSelectedById(R.id.checkboxModoEscuro)) {
            escolhas.add("modo escuro");
        }
        if (isCheckboxSelectedById(R.id.checkboxLembrarLogin)) {
            escolhas.add("lembrar login");
        }

        exibirEscolhas(escolhas);
    }
}