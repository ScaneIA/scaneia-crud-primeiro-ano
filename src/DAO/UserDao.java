package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean insert(){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO USUARIO (NOME, EMAIL, CPF) VALUES (?, ?, ?)")) {
                pstmt.setString(1,"Gustavo Amex");
                pstmt.setString(2, "gustavo.amex@gmail.com");
                pstmt.setInt(3, 569874534);
                pstmt.execute();

            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                conexao.desconectar();
            }
            return true;
        }

    }

    public boolean update(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt= conn.prepareStatement("UPDATE USUARIO SET NOME=? WHERE CPF=? ");
                pstmt.setString(1,"Aline");
                pstmt.setInt(2,569874534);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            finally {
                conexao.desconectar();
            }
            return true;
        }
    }
    public boolean delete(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if(conn==null){
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try{
                String remover="DELETE FROM USUARIO WHERE CPF=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,569874532);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            finally {
                conexao.desconectar();
            }
            return true;
        }

    }

    public boolean login(String email, String senha){
        //variables
        int usersFound = 0;

        //create connection
        Conexao conexao = new Conexao();
        ResultSet resultSet;

        //realize the query
        try {
            //prepare the query
            String sql = "SELECT COUNT(*) FROM USUARIO WHERE email = ? and senha = ?";
            PreparedStatement preparedStatement = conexao.getConnection().prepareStatement(sql);

            //change the parameters
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            //do the query
            resultSet = preparedStatement.executeQuery();

        }catch (SQLException exception){
            System.out.println("Erro ao fazer consulta");
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }

        //See the amount of founded users
        try{
            if (resultSet.next()){
                usersFound = resultSet.getInt(1);
            }

        }catch (SQLException exception){
            System.out.println("Erro ao visualizar consulta");
            exception.printStackTrace();
            return false;
        }

        //return the result
        return usersFound == 1;
    }
}
