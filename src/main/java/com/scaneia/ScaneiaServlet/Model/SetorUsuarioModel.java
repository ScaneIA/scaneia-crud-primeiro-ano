package com.scaneia.ScaneiaServlet.Model;

// Model criado para representar a tabela intermediária que existe entre setor e usuário
public class SetorUsuarioModel {
    //atributos
    private int id;
    private int idUsuario;
    private int idSetor;

    //construtor
    public SetorUsuarioModel(int id, int idUsuario, int idSetor) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idSetor = idSetor;
    }

    //getters
    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdSetor() {
        return idSetor;
    }
}
