package com.scaneia.ScaneiaServlet.Model;

import java.time.*;

public class SetorModel {
    //atributos
    private int id;
    private String descricao; //text
    private String nome; //max 80 chars
    private LocalDateTime dataCriacao; //timestamp z
    private LocalDateTime dataAtualizacao; //timestamp z
    private LocalDateTime dataExclusao; //timestamp z
    private int idArea;

    //construtor com todos atributos, ideal para buscas
    public SetorModel(int id, String descricao, String nome, LocalDateTime dataAtualizacao, LocalDateTime dataExclusao, int idArea,
                      LocalDateTime dataCriacao) {
        this.id = id;
        this.descricao = descricao;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
        this.idArea = idArea;
    }

    //construtor simples, ideal para inserts
    public SetorModel(String descricao, String nome, int idArea) {
        this.descricao = descricao;
        this.nome = nome;
        this.idArea = idArea;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public int getIdArea() {
        return idArea;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    //setters
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
}
