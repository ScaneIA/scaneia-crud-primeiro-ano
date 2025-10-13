package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class UsuarioModel {
    //atributos
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String urlFoto; //tipo text
    private String senha;
    private LocalDateTime dataCriacao; //LocalDateTime timezone z
    private LocalDateTime dataAtualizacao; //LocalDateTime timezone z
    private LocalDateTime dataExclusao; //LocalDateTime timezone z
    private int idCargo;

    //construtor com todos os atributos, ideal para buscas
    public UsuarioModel(int id, String nome, String email, String cpf, String urlFoto, String senha,
                        LocalDateTime dataAtualizacao,
                        LocalDateTime dataExclusao, int idCargo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.urlFoto = urlFoto;
        this.senha = senha;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
        this.idCargo = idCargo;
        this.dataCriacao = dataCriacao;
    }

    //Construtor simples, ideal para registros
    public UsuarioModel(String nome, String email, String cpf, String senha, int idCargo) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.idCargo = idCargo;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    //Setters necessários
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public void setId(int id) {
        this.id = id;
    }
}