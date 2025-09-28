package Model;

import java.time.LocalDateTime;

public class UsuarioModel {
    //atributos
    private int id;
    private String nome; //max 80 chars
    private String email; //max 200 chars
    private String cpf; //max 12 chars
    private String urlFoto; //tipo text
    private String senha; //max 200 chars
    private LocalDateTime dataCriacao; //LocalDateTime timezone z
    private LocalDateTime dataAtualizacao; //LocalDateTime timezone z
    private LocalDateTime dataExclusao; //LocalDateTime timezone z
    private int idCargo;

    //construtor com todos os atributos, ideal para buscas
    public UsuarioModel(int id, String nome, String email, String cpf, String urlFoto, String senha,
                        LocalDateTime dataAtualizacao,
                        LocalDateTime dataExclusao, int idCargo, LocalDateTime dataCriacao) {
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
    public UsuarioModel(String nome, String email, String cpf, String senha, int idCargo) {
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

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getDataCriacao() {
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

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public void setId(int id) {
        this.id = id;
    }
}