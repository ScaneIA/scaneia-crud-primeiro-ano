package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection connection;

    public Conexao(){
        try{
            //carrega o driver
            Class.forName("org.postgresql.Driver");

            //pega as variaveis de ambiente
            String url = System.getenv("JDBC_URL");
            String user = System.getenv("JDBC_USER_NAME");
            String password = System.getenv("JDBC_PASS_WORD");

            //cria a conexão
            connection = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException clnfe){
            clnfe.printStackTrace();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void desconectar(){
        try {
            this.connection.close(); //fecha a conexão
        }catch (SQLException sqle){
            System.out.println("Não foi possivel fechar a conexão");
        }
    }
}
