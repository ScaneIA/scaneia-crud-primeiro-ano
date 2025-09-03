package TestDAO;

import DAO.Conexao;
import DAO.UserDAO;

public class TestUserDAO {
    public static void main(String[] args) {
        //variables
        Conexao conexao = new Conexao();
        UserDAO userDAO = new UserDAO();

        //test the connection
        if (conexao.getConnection() == null){
            System.out.println("Não foi possivel conectar ao banco de dados");
            return;
        }else {
            System.out.println("conectado ao banco de dados com sucesso");
        }

        //test the login
        if (userDAO.login("natalia.flores@gmail.com", "teste")){
            System.out.println("Usuario encontrado com sucesso");
        }else{
            System.out.println("Usuario não encontrado");
        }
        conexao.desconectar();
    }
}
