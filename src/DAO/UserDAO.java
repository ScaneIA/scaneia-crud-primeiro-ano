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
                    "INSERT INTO USUARIOS (NOME, EMAIL, CPF) VALUES (?, ?, ?)")) {
                pstmt.setString(1,"Gustavo Amex");
                pstmt.setString(2, "gustavo.amex@gmail.com");
                pstmt.setInt(3, 569874530);
                pstmt.execute();

            } catch (SQLException se) {
                se.printStackTrace();
                return false;
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
                PreparedStatement pstmt= conn.prepareStatement("UPDATE USUARIOS SET NOME=? WHERE CPF=? ");
                pstmt.setString(1,"Aline");
                pstmt.setInt(2,569874534);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
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
                String remover="DELETE FROM USUARIOS WHERE CPF=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,569874532);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
            return true;
        }

    }

    public boolean login(String email, String senha){
        //variavei
        int usersFound = 0;

        //Cria uma conexão
        Conexao conexao = new Conexao();
        ResultSet resultSet;

        //try-catch para a consulta
        try {
            //Prepara a consulta
            String sql = "SELECT COUNT(*) FROM USUARIOS WHERE email = ? and senha = ?";
            PreparedStatement preparedStatement = conexao.getConnection().prepareStatement(sql);

            //Modifica os parametros
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            //Faz a consulta
            resultSet = preparedStatement.executeQuery();

        }catch (SQLException exception){
            System.out.println("Erro ao fazer consulta");
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }

        //Verifica a quantidade de usuarios encontrados
        try{
            if (resultSet.next()){
                usersFound = resultSet.getInt(1);
            }

        }catch (SQLException exception){
            System.out.println("Erro ao visualizar consulta");
            exception.printStackTrace();
            return false;
        }

        //Retorna se tem apenas 1 usuario encontrado
        return usersFound == 1;
    }
}
