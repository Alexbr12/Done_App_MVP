package com.example.done_app_mvp.model;

public class Pessoa {

    private  String name, cpf, nascimento;
    private  String classe, profissao,avatar , curriculo;
    public   Double log, lat;

    public Pessoa(){
        this.name = "";
        this.cpf = "";
        this.profissao = "";
        this.nascimento = "";
        this.avatar = "";
        this.log = 0.0;
        this.lat = 0.0;
    }

    public Pessoa(String name, String cpf, String profissao, String avatar) {
        this.name = name;
        this.cpf = cpf;
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

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nascimento='" + nascimento + '\'' +
                ", classe='" + classe + '\'' +
                ", profissao='" + profissao + '\'' +
                '}';
    }
}
