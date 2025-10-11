package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class EmpresaModel {
    private int id;
    private String nome;
    private String cnpj;
    private String senha;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;

    private String email;

    public EmpresaModel() {}

    public EmpresaModel(String nome,String cnpj,String email,String senha) {
        this.nome = nome;
        this.cnpj=cnpj;
        this.email=email;
        this.senha=senha;
    }

    public EmpresaModel(int id, String nome, String cnpj,String email,String senha,LocalDateTime dataCriacao ,LocalDateTime dataAtualizacao, LocalDateTime dataExclusao ) {
        this.id = id;
        this.nome = nome;
        this.cnpj=cnpj;
        this.email=email;
        this.senha=senha;
        this.dataCriacao=dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;


    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }



    @Override

    public String toString() {
        return "EmpresaModel:"+ "id=" + id + ", nome='" + nome + "', cnpj='" + cnpj + "', email='" + email + "', senha='" + senha + "', dataExclusao=" + dataExclusao + ", dataAtualizacao=" + dataAtualizacao + "}";
    }

}