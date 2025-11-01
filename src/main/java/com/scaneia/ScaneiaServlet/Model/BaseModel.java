package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

// Base usada como modelo para área, cargo e setor
public abstract class BaseModel {
    private int id;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;

    // Construtor simples, para quando nem tudo é preenchido
    public BaseModel(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Outro construtor para pegar id também
    public BaseModel(int id, String nome, String descricao){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Construtor completo
    public BaseModel(int id, String nome, String descricao, LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataExclusao = dataExclusao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataExclusao() { return dataExclusao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setDataExclusao(LocalDateTime dataExclusao) { this.dataExclusao = dataExclusao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    // toString
    @Override
    public String toString() {
        return "id=" + id + ", nome='" + nome + '\n' +
                ", descricao='" + descricao + '\n' +
                ", dataExclusao=" + dataExclusao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", dataCriacao=" + dataCriacao;
    }
}
