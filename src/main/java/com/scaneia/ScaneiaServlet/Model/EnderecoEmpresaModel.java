package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class EnderecoEmpresaModel {
    private int id;
    private String estado;
    private String cep;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String complemento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;
    private int idEmpresa;

    public EnderecoEmpresaModel() {}

    public EnderecoEmpresaModel(String estado, String cep, String cidade, String bairro, String rua, int numero, String complemento, int idEmpresa) {
        this.estado = estado;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.idEmpresa = idEmpresa;
        this.dataCriacao = LocalDateTime.now();
    }

    public EnderecoEmpresaModel(int id, String estado, String cep, String cidade, String bairro, String rua, int numero, String complemento, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, LocalDateTime dataExclusao, int idEmpresa) {
        this.id = id;
        this.estado = estado;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
        this.idEmpresa = idEmpresa;
    }

    // Getters
    public int getId() { return id; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }
    public String getCidade() { return cidade; }
    public String getBairro() { return bairro; }
    public String getRua() { return rua; }
    public int getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public LocalDateTime getDataExclusao() { return dataExclusao; }
    public int getIdEmpresa() { return idEmpresa; }

    // Setters apenas para id e idEmpresa
    public void setId(int id) { this.id = id; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    @Override
    public String toString() {
        return "EnderecoEmpresaModel{id=" + id + ", estado='" + estado + "', cep='" + cep + "', cidade='" + cidade + "', bairro='" + bairro + "', rua='" + rua + "', numero=" + numero + ", complemento='" + complemento + "', dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", dataExclusao=" + dataExclusao + ", idEmpresa=" + idEmpresa + "}";
    }
}
