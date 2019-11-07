package com.example.done_app_mvp.model;

import java.util.ArrayList;

public class Profissao {

    String nomeProfissao;
    ArrayList<Pessoa> pessoas = new ArrayList<>();

    public Profissao(String nome, ArrayList<Pessoa> pessoas){
        this.nomeProfissao = nome;
        this.pessoas = pessoas;
    }
}
