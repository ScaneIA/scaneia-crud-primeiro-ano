package TestDAO;

import DAO.Conexao;
import DAO.UserDAO;


public class ConexaoTeste {
    public static void main(String[] args) {
        //variaveis
        Conexao conexao = new Conexao();
        UserDAO test= new UserDAO();

        //testando a conexão com o DB
        if (conexao.getConnection() == null){
            System.out.println("Não foi possivel conectar");
        }else {
            System.out.println("Conectado com sucesso");
        }

        //testa um registro de usuario
        if(test.insert()){
            System.out.println("Inserção realizada");
        }
        else{
            System.out.println("Não foi possível inserir");
        }

        //faz um login de usuario
        if (test.login("natalia.flores@gmail.com", "teste")){
            System.out.println("Login feito com sucesso");
        }else{
            System.out.println("O login não foi feito");
        }

        //atualiza um registro de usuario
        if(test.update()){
            System.out.println("Atualização realizada");
        }
        else{
            System.out.println("Não foi possível atualizar");
        }

        //apaga um registro
        if(test.delete()){
            System.out.println("Usuario deletado");
        }
        else{
            System.out.println("Não foi possível deletar");
        }
        conexao.desconectar();
    }
}
