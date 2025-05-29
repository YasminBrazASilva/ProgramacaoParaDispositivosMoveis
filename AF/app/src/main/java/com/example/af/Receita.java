package com.example.af;

import java.util.List;

public class Receita {
    private String id;
    private String nome;
    private List<String> ingredientes;
    private List<String> passos;

    public Receita() {
    }

    public Receita(String id, String nome, List<String> ingredientes, List<String> passos) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.passos = passos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPassos() {
        return passos;
    }

    public void setPassos(List<String> passos) {
        this.passos = passos;
    }
}
