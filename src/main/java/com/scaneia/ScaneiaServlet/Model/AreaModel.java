package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

public class AreaModel extends BaseModel {


    //construtor simples, ideal para registros
    public AreaModel(String nome, String descricao) {
        super(nome, descricao);
    }

    //construtor com todos os atributos, ideal para buscas
    public AreaModel(int id, String nome, String descricao,
                      LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao) {
        super(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
    }
}

