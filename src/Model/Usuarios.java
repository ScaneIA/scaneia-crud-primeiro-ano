package Model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Usuarios {
    //atributos
    private int id;
    private String nome; //max 80 chars
    private String email; //max 200 chars
    private String cpf; //max 12 chars
    private String urlFoto; //tipo text
    private String senha; //max 200 chars
    private Timestamp dataCriacao; //timestamp timezone z
    private Timestamp dataAtualizacao; //timestamp timezone z
    private Timestamp dataExclusao; //timestamp timezone z
    private int idCargo;

    //construtor com todos os atributos, ideal para buscas
    public Usuarios(int id, String nome, String email, String cpf, String urlFoto, String senha, Timestamp dataAtualizacao, Timestamp dataExclusao, int idCargo, Timestamp dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.urlFoto = urlFoto;
        this.senha = senha;
        this.dataAtualizacao = dataAtualizacao;
        this.dataExclusao = dataExclusao;
        this.idCargo = idCargo;
        this.dataCriacao = dataCriacao;
    }

    //construtor simples, ideal para registros
    public Usuarios(String nome, String email, String cpf, String senha, int idCargo) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.idCargo = idCargo;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Timestamp getDataExclusao() {
        return dataExclusao;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getSenha() {
        return senha;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    //setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
}