package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

// Áreas da empresa
// Herda classe BaseModel
public class AreaModel extends BaseModel {

    // Construtor simples, quando nem tudo é preenchido
    public AreaModel(String nome, String descricao) {
        super(nome, descricao);
    }

    // Construtor completo
    public AreaModel(int id, String nome, String descricao,
                     LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao) {
        super(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
    }
}
