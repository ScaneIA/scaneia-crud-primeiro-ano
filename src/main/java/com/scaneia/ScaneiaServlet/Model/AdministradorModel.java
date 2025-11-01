package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

// Classe o adm para acessar a área restrita
public class AdministradorModel {
    // Atributos
    private int id;
    private String email;
    private String senha;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;

    // Construtor simples, para quando nem todos os dados são preenchidos
    public AdministradorModel(int id, String email, String senha){
        this.id = id;
        this.senha = senha;
        this.email = email;
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
