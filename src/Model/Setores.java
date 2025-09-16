package Model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Setores {
    //atributos
    private int id;
    private String descricao; //text
    private String nome; //max 80 chars
    private Timestamp dataAtualizacao; //timestamp z
    private Timestamp dataExclusao; //timestamp z
    private int idArea;

    //construtor com todos atributos, ideal para buscas
    public Setores(int id, String descricao, String nome, Timestamp dataCriacao, Timestamp dataExclusao, int idArea) {
        this.id = id;
        this.descricao = descricao;
        this.nome = nome;
        this.dataAtualizacao = dataCriacao;
        this.dataExclusao = dataExclusao;
        this.idArea = idArea;
    }

    //construtor simples, ideal para inserts
    public Setores(String descricao, String nome, int idArea) {
        this.descricao = descricao;
        this.nome = nome;
        this.idArea = idArea;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Timestamp getDataExclusao() {
        return dataExclusao;
    }

    public int getIdArea() {
        return idArea;
    }

    //setters
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        //cria a data em instante
        Instant instant = dataAtualizacao.toInstant();

        //cria com fuso-horario certo
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);

        //motifica a data com o fuso-horario correto
        this.dataAtualizacao = Timestamp.from(zonedDateTime.toInstant());
    }

    public void setDataExclusao(Timestamp dataExclusao) {
        //cria a data em instante
        Instant instant = dataAtualizacao.toInstant();

        //cria com fuso-horario certo
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);

        //motifica a data com o fuso-horario correto
        this.dataExclusao = Timestamp.from(zonedDateTime.toInstant());
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
}
