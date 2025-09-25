package Model;

import java.time.LocalDateTime;

public class CargoModel {
    private int id;
    private String nome;
    private String descricao;
    private LocalDateTime dataExclusao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataCriacao;

    public CargoModel() {}

    public CargoModel(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public CargoModel(int id, String nome, String descricao, LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataExclusao = dataExclusao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataCriacao = dataCriacao;
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

    public String getDescricao() {
        return descricao;
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


    @Override
    public String toString() {
        return "AreaModel" + "id=" + id + ", nome='" + nome + '\n' + ", descricao='" + descricao + '\n' + ", dataExclusao=" + dataExclusao + ", dataAtualizacao=" + dataAtualizacao + ", dataCriacao=" + dataCriacao;
    }
}

