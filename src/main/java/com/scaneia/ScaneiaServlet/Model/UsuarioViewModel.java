package com.scaneia.ScaneiaServlet.Model;

// Model criado para facilitar o select entre as tabelas do banco, ao ínves de um código com vários joins, fizemos uma view,
// com essa view as tabelas cargo e setor são apresentadas no próprio usuário ao invés do ID delas, facilitando a
// indentificação do usuário e diminuido a necessidade de mais páginas de CRUD já que em um CRUD só incluimos 3 ao mesmo
// tempo para simplificar a ação do usuário

// qualquer dúvida sobre o comentário acima acesse a documentação
public class UsuarioViewModel {
    //atributos
    int id;
    String nome;
    String email;
    String cpf;
    String setor;
    String cargo;
    byte[] urlFoto;
    String registro;

    //construtor proprio para consultas da view

    public UsuarioViewModel(int id, String nome, String email, String cpf, String setor, String cargo, byte[] urlFoto,
                            String registro) {
        this.id = id;
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

    public byte[] getUrlFoto() {
        return urlFoto;
    }

    public String getRegistro() {
        return registro;
    }

    public int getId() {
        return id;
    }

    //metodos gerais
    private String formatarCPF(String cpf){
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }
}
