package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class SetorModel extends BaseModel {
    // atributo
    private int idArea;


    //construtor simples, ideal para registros
    public SetorModel(String nome, String descricao, int idArea) {
        super(nome, descricao);
        this.idArea = idArea;
    }
    //construtor com todos os atributos, ideal para buscas
    public SetorModel(int id, String nome, String descricao,
                      LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao,
                      int idArea) {
        super(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
        this.idArea = idArea;
    }

    // Getter e Setter específicos
    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    @Override
    public String toString() {
        return super.toString() + ", idArea=" + idArea;
    }
}

