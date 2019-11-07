package com.example.done_app_mvp.model;

public class Pessoa {

    private  String name, cpf, regiao;
    private  String profissao,avatar , curriculo;
    public String id;

    public Pessoa(){
        this.name = "";
        this.cpf = "";
        this.regiao = "";
        this.profissao = "";
        this.avatar = "link";
    }

    public Pessoa(String name, String cpf, String regiao, String profissao, String avatar) {
        this.name = name;
        this.cpf = cpf;
        this.regiao = regiao;
        this.profissao = profissao;
        this.avatar = "link";
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }




}
