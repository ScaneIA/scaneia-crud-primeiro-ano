package com.scaneia.ScaneiaServlet.Model;

public class UsuarioViewModel {
    //atributos
    String nome;
    String email;
    String cpf;
    String setor;
    String cargo;
    String urlFoto;
    String registro;

    //construtor proprio para consultas da view

    public UsuarioViewModel(String nome, String email, String cpf, String setor, String cargo, String urlFoto, String registro) {
        this.nome = nome;
        this.email = email;
        this.cpf = formatarCPF(cpf);
        this.setor = setor;
        this.cargo = cargo;
        this.urlFoto = urlFoto;
        this.registro = registro;
    }


    //getters

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSetor() {
        return setor;
    }

    public String getCargo() {
        return cargo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getRegistro() {
        return registro;
    }

    //metodos gerais
    private String formatarCPF(String cpf){
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }
}
