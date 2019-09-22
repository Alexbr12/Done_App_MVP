package com.example.done_app_mvp.cadastro;

public class Pessoa {

    private  String name, cpf, regiao;
    private  String profissao, interesse;

    public Pessoa(String name, String cpf, String regiao, String profissao, String interesse) {
        this.name = name;
        this.cpf = cpf;
        this.regiao = regiao;
        this.profissao = profissao;
        this.interesse = interesse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getInteresse() {
        return interesse;
    }

    public void setInteresse(String interesse) {
        this.interesse = interesse;
    }




}
