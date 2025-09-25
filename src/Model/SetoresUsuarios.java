package Model;

public class SetoresUsuarios {
    //atributos
    private int id;
    private int idUsuario;
    private int idSetor;

    //construtor
    public SetoresUsuarios(int id, int idUsuario, int idSetor) {
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
