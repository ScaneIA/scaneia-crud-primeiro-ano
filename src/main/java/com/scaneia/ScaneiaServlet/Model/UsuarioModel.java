package com.scaneia.ScaneiaServlet.Model;

import jakarta.servlet.http.Part;

import java.time.LocalDateTime;

// Usuários da empresa
public class UsuarioModel {
    //atributos
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private byte[] urlFoto; //tipo text
    private String senha;
    private LocalDateTime dataCriacao; //LocalDateTime timezone z
    private LocalDateTime dataAtualizacao; //LocalDateTime timezone z
    private LocalDateTime dataExclusao; //LocalDateTime timezone z
    private int idCargo;
    private int idEmpresa;

    //construtor com todos os atributos, ideal para buscas
    public UsuarioModel(int id, String nome, String email, String cpf, byte[] urlFoto, String senha, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, LocalDateTime dataExclusao, int idCargo, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.urlFoto = urlFoto;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
        this.idCargo = idCargo;
        this.idEmpresa = idEmpresa;
    }

    //Construtor simples, ideal para registros
    public UsuarioModel(String nome, String email, String cpf, int idCargo, int idEmpresa, byte[] urlFoto) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.idCargo = idCargo;
        this.idEmpresa = idEmpresa;
        this.urlFoto = urlFoto;
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

    public byte[] getUrlFoto() {
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

    public int getIdEmpresa() {
        return idEmpresa;
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