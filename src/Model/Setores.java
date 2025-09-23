package Model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Setores {
    //atributos
    private int id;
    private String descricao; //text
    private String nome; //max 80 chars
    private Timestamp dataCriacao; //timestamp z
    private Timestamp dataAtualizacao; //timestamp z
    private Timestamp dataExclusao; //timestamp z
    private int idArea;

    //construtor com todos atributos, ideal para buscas
    public Setores(int id, String descricao, String nome, Timestamp dataAtualizacao, Timestamp dataExclusao, int idArea,
                   Timestamp dataCriacao) {
        this.id = id;
        this.descricao = descricao;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
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

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    //setters
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        //cria a data em zonedDateTime
        Instant instante = dataAtualizacao.toInstant();
        ZonedDateTime areaAtual = ZonedDateTime.ofInstant(instante, ZoneId.systemDefault());

        //mantem o instante em outro timezone
        ZonedDateTime convertido = areaAtual.withZoneSameInstant(ZoneOffset.UTC);

        //coloca o tempo convertido no instante
        this.dataAtualizacao = Timestamp.from(Instant.from(convertido));
    }

    public void setDataExclusao(Timestamp dataExclusao) {
        //cria a data em zonedDateTime
        Instant instante = dataExclusao.toInstant();
        ZonedDateTime areaAtual = ZonedDateTime.ofInstant(instante, ZoneId.systemDefault());

        //mantem o instante em outro timezone
        ZonedDateTime convertido = areaAtual.withZoneSameInstant(ZoneOffset.UTC);

        //coloca o tempo convertido no instante
        this.dataExclusao = Timestamp.from(Instant.from(convertido));
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
}
