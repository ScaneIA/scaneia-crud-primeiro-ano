package com.scaneia.ScaneiaServlet.conexao;

import com.scaneia.ScaneiaServlet.Config.EnvConfig;

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
            String url = EnvConfig.getEnv("JDBC_URL");
            String user = EnvConfig.getEnv("JDBC_USER_NAME");
            String password = EnvConfig.getEnv("JDBC_PASS_WORD");

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
            //fecha a conexão
            this.connection.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
