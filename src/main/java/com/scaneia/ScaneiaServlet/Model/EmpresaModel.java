package com.scaneia.ScaneiaServlet.Model;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;

import java.time.LocalDateTime;

public class EmpresaModel {
    // atributos
    private int id;
    private String nome;
    private String cnpj;
    private String senha;
    private LocalDateTime dataCriacao; //LocalDateTime timezone z
    private LocalDateTime dataAtualizacao; //LocalDateTime timezone z
    private LocalDateTime dataExclusao; //LocalDateTime timezone z

    private String email;

    //construtor simples, ideal para registros
    public EmpresaModel(String nome,String cnpj,String email,String senha) {
        this.nome = nome;
        this.cnpj=cnpj;
        this.email=email;
        this.senha=senha;
    }

    //construtor médio, ideal para validar credenciais
    public EmpresaModel(int id, String nome, String cnpj, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.senha = senha;
        this.email = email;
    }

    //construtor com todos os atributos, ideal para buscas
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
    public EmpresaModel(int id, String nome, String cnpj,String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj=cnpj;
        this.email=email;
    }

    // Getters
    public int getId() {
        return id;
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
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    // Setters necessários
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override

    public String toString() {
        return "EmpresaModel:"+ "id=" + id + ", nome='" + nome + "', cnpj='" + cnpj + "', email='" + email + "', senha='" + senha + "', dataExclusao=" + dataExclusao + ", dataAtualizacao=" + dataAtualizacao + "}";
    }

}