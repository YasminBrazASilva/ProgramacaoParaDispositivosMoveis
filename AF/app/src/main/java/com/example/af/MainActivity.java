package com.example.af;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    EditText edtEmail;
    EditText edtSenha;

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

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        db = FirebaseFirestore.getInstance();
    }

    private void limparCampos() {
        edtEmail.setText("");
        edtSenha.setText("");
    }

    public void exibirToast(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        Log.d("FIREBASE", texto);

    }

    public void cadastrarUsuario(View v)
    {

        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        exibirToast("Usuário criado com sucesso");
                    } else {
                        exibirToast("Erro ao criar usuário: " + task.getException());
                    }
                });

    }

    public void logarUsuario(View v)
    {
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenha);

        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        exibirToast("Login bem-sucedido");
                        limparCampos();

                        Intent intent = new Intent(MainActivity.this, PaginaInicial.class);
                        startActivity(intent);

                    } else {
                        exibirToast("Erro no login: " + task.getException());
                    }
                });
    }
}