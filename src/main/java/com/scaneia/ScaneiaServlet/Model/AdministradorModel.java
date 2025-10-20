package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class AdministradorModel {
    private int id;
    private String email;
    private String senha;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;

    public AdministradorModel() {}

    public AdministradorModel(int id, String email, String senha,
                              LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, LocalDateTime dataExclusao) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public LocalDateTime getDataExclusao() { return dataExclusao; }
    public void setDataExclusao(LocalDateTime dataExclusao) { this.dataExclusao = dataExclusao; }
}
