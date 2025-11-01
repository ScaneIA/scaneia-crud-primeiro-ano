package com.scaneia.ScaneiaServlet.Model;

import java.time.LocalDateTime;

// Cargo dentro da empresa
// Herda os atributos e métodos da classe BaseModel
public class CargoModel extends BaseModel {


    // Construtor completo
    public CargoModel(int id, String nome, String descricao,
                      LocalDateTime dataExclusao, LocalDateTime dataAtualizacao, LocalDateTime dataCriacao) {
        super(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
    }
}
